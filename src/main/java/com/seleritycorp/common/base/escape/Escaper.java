/*
 * Copyright (C) 2016-2018 Selerity, Inc. (support@seleritycorp.com)
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

package com.seleritycorp.common.base.escape;

import com.google.common.html.HtmlEscapers;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * General purpose escapers.
 */
@Singleton
public class Escaper {
  private final com.google.common.escape.Escaper htmlEscaper;

  @Inject
  Escaper() {
    htmlEscaper = HtmlEscapers.htmlEscaper();
  }

  /**
   * Encodes raw strings for use in plain text nodes
   *
   * @param rawString The raw string that should get escaped.
   * @return The escaped raw string.
   */
  public String html(String rawString) {
    String ret = "";
    if (rawString != null) {
      ret = htmlEscaper.escape(rawString);
    }
    return ret;
  }
}
