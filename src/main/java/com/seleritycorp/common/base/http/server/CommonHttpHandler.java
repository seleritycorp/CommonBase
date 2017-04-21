/*
 * Copyright (C) 2016-2017 Selerity, Inc. (support@seleritycorp.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.seleritycorp.common.base.http.server;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import com.seleritycorp.common.base.state.AppStateManager;

import java.io.IOException;

import javax.servlet.ServletException;

/**
 * Http Handler for pages and tasks on all applications. 
 */
public class CommonHttpHandler extends AbstractHttpHandler {
  interface Factory {
    CommonHttpHandler create(AbstractHttpHandler delegate);
  }

  private final AbstractHttpHandler delegateHttpHandler;
  private final AppStateManager appStateManager;
  private final HttpHandlerUtils utils;
  
  @Inject
  CommonHttpHandler(@Assisted AbstractHttpHandler delegateHttpHandler,
      AppStateManager appStateManager, HttpHandlerUtils utils) {
    this.delegateHttpHandler = delegateHttpHandler;
    this.appStateManager = appStateManager;
    this.utils = utils;
  }

  @Override
  public void handle(String target, HandleParameters params) throws IOException,
      ServletException {
    switch (target) {
      case "/status":
        if (utils.isMethodGet(params)) {
          String sender = utils.resolveRemoteAddr(params);
          if (sender.startsWith("10.") || sender.startsWith("127.")) {
            utils.respond(appStateManager.getStatusReport(), params);
          } else {
            utils.respondForbidden(params);
          }
        } else {
          utils.respondBadRequest("Target " + target + " expects GET method", params);
        }
        break;
      default:
        delegateHttpHandler.handle(target, params);
        break;
    }

    if (!utils.isHandled(params)) {
      utils.respondNotFound(params);
    }
  }  
}