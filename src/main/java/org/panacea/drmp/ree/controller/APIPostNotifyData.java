package org.panacea.drmp.ree.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.panacea.drmp.ree.domain.notification.DataNotification;
import org.panacea.drmp.ree.domain.notification.DataNotificationResponse;
import org.panacea.drmp.ree.service.OrchestratorNotificationHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(path = "/ree", produces = "application/json")
public class APIPostNotifyData {

    @Autowired
    OrchestratorNotificationHandlerService orchestratorNotificationHandler;

    @Operation(description = "Post a Data Notification")
    @PostMapping(value = "/notify/data")
    public DataNotificationResponse postNotifyData(@RequestBody DataNotification request) {
        return orchestratorNotificationHandler.perform(request);
    }

}