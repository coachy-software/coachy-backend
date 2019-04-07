package life.coachy.backend.infrastructure.permission;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.bson.types.ObjectId;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
class PermissionAspect {

  @Around("@annotation(RequiresPermissions)")
  public Object permission(ProceedingJoinPoint joinPoint) throws Throwable {
    List<Boolean> permissionsValues = Lists.newArrayList();
    this.addPermissionsValues(joinPoint, permissionsValues, this.getHexObjectId(joinPoint));

    if (!permissionsValues.contains(false)) {
      return joinPoint.proceed();
    }

    throw new InsufficientPermissionsException();
  }

  private String getHexObjectId(ProceedingJoinPoint joinPoint) {
    for (Object arg : joinPoint.getArgs()) {
      if (arg.getClass().equals(ObjectId.class)) {
        return String.valueOf(arg);
      }
    }
    return "";
  }

  private void addPermissionsValues(ProceedingJoinPoint joinPoint, List<Boolean> permissionsValues, String hexObjectId) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    RequiresPermissions myAnnotation = signature.getMethod().getAnnotation(RequiresPermissions.class);

    String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    Set<String> permissions = this.mapPermissionsStringsToSet(principal, hexObjectId);

    for (String permission : myAnnotation.value()) {
      String formattedPermission = StringUtils.replace(permission, "{id}", hexObjectId);
      permissionsValues.add(permissions.contains(formattedPermission));
    }
  }

  private Set<String> mapPermissionsStringsToSet(String principalContent, String hexObjectId) {
    return Stream.of(this.getPermissionsString(principalContent).split(","))
        .map(String::trim)
        .map(permission -> permission.replace("{id}", hexObjectId))
        .collect(Collectors.toSet());
  }

  private String getPermissionsString(String principalContent) {
    int lastIndexOfPermissions = principalContent.lastIndexOf("permissions");
    int lastIndexOfBoardIdentifier = principalContent.indexOf("boardIdentifier");

    String rawBracketedPermissions = this.removeLastTwoChars(principalContent.substring(lastIndexOfPermissions, lastIndexOfBoardIdentifier));
    String unbracketedPermissions = this.removeBrackets(rawBracketedPermissions);

    return StringUtils.replace(unbracketedPermissions, "permissions=", "");
  }

  private String removeBrackets(String string) {
    String withoutLeftBracket = StringUtils.replace(string, "[", "");
    return StringUtils.replace(withoutLeftBracket, "]", "");
  }

  private String removeLastTwoChars(String string) {
    return string.substring(0, string.length() - 1);
  }

}

