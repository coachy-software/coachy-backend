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

  private String idPattern = "{id}";

  @Around("@annotation(life.coachy.backend.infrastructure.permission.RequiresPermission)")
  public Object permission(ProceedingJoinPoint joinPoint) throws Throwable {
    List<Boolean> permissionsValues = Lists.newArrayList();
    this.addPermissionsValues(joinPoint, permissionsValues, this.obtainProperHexObjectId(joinPoint));

    if (!permissionsValues.contains(false)) {
      return joinPoint.proceed();
    }

    throw new InsufficientPermissionsException();
  }

  private String obtainProperHexObjectId(ProceedingJoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Object[] args = joinPoint.getArgs();

    this.obtainIdPattern(signature.getMethod().getAnnotation(RequiresPermission.class));

    for (int i = 0; i < joinPoint.getArgs().length; i++) {
      Object arg = args[i];
      String name = signature.getParameterNames()[i];

      if (arg.getClass().equals(ObjectId.class) && this.idPattern.equals(name)) {
        return String.valueOf(arg);
      }
    }

    return "";
  }

  private void addPermissionsValues(ProceedingJoinPoint joinPoint, List<Boolean> permissionsValues, String hexObjectId) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    RequiresPermission annotation = signature.getMethod().getAnnotation(RequiresPermission.class);

    String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    Set<String> permissions = this.mapPermissionsStringsToSet(principal, hexObjectId);

    String formattedPermission = StringUtils.replace(annotation.value(), "{" + this.idPattern + "}", hexObjectId);
    permissionsValues.add(permissions.contains(formattedPermission));
  }

  private void obtainIdPattern(RequiresPermission annotation) {
    String permission = annotation.value();

    int leftBracket = permission.indexOf("{");
    int rightBracket = permission.indexOf("}");

    this.idPattern = permission.substring(leftBracket + 1, rightBracket);
  }

  private Set<String> mapPermissionsStringsToSet(String principalContent, String hexObjectId) {
    return Stream.of(this.getPermissionsString(principalContent).split(","))
        .map(String::trim)
        .map(permission -> permission.replace(this.idPattern, hexObjectId))
        .collect(Collectors.toSet());
  }

  private String getPermissionsString(String principalContent) {
    int lastIndexOfPermissions = principalContent.lastIndexOf("permissions");
    int lastIndexOfBoardId = principalContent.indexOf("boardId");

    String rawBracketedPermissions = this.removeLastTwoChars(principalContent.substring(lastIndexOfPermissions, lastIndexOfBoardId));
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

