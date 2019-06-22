package life.coachy.backend.notification;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.HashMap;
import java.util.Map;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.infrastructure.permission.RequiresPermissions;
import life.coachy.backend.notification.domain.NotificationFacade;
import life.coachy.backend.notification.query.NotificationQueryDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.NOTIFICATIONS)
class NotificationEndpoint {

  private final NotificationFacade notificationFacade;

  @Autowired
  NotificationEndpoint(NotificationFacade notificationFacade) {
    this.notificationFacade = notificationFacade;
  }

  @ApiOperation("Displays all notifications belonging to specified user's id")
  @RequiresPermissions("user.{recipientId}.read")
  @RequiresAuthenticated
  @GetMapping("{recipientId}")
  Page<NotificationQueryDto> fetchAllByRecipientId(@PathVariable @ApiParam("Notification's recipient id") ObjectId recipientId,
      @PageableDefault(size = 5) Pageable pageable) {
    return this.notificationFacade.fetchAllByRecipientId(recipientId, pageable);
  }

  @ApiOperation("Marks all notifications belonging to specified user as read")
  @RequiresPermissions("user.{recipientId}.read")
  @RequiresAuthenticated
  @PostMapping("{recipientId}/mark-as-read")
  ResponseEntity<?> markAllAsRead(@PathVariable @ApiParam("Notification's recipient id") ObjectId recipientId) {
    this.notificationFacade.markAllAsRead(recipientId);
    return ResponseEntity.ok().build();
  }

  @ApiOperation("Returns true/false if user has or has not any unread notification")
  @RequiresPermissions("user.{recipientId}.read")
  @RequiresAuthenticated
  @GetMapping("{recipientId}/has-unread")
  ResponseEntity<Map<String, Object>> hasAnyUnread(@PathVariable @ApiParam("Notification's recipient id") ObjectId recipientId) {
    return ResponseEntity.ok(new HashMap<String, Object>() {{
      this.put("hasUnread", NotificationEndpoint.this.notificationFacade.hasAnyUnread(recipientId));
      this.put("count", NotificationEndpoint.this.notificationFacade.countUnreadNotifications(recipientId));
    }});
  }

}
