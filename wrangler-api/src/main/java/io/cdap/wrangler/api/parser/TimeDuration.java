/*
 * Copyright © 2017-2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.cdap.wrangler.api.parser;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * TimeDuration Token - parses strings like "200ms", "2s", "3m", "1.5h" into milliseconds.
 */
public class TimeDuration implements Token {
  private final String originalValue;
  private final long milliseconds;

  public TimeDuration(String value) {
    this.originalValue = value;

    String input = value.trim().toLowerCase();

    if (input.endsWith("ms")) {
      milliseconds = (long)(Double.parseDouble(input.replace("ms", "")));
    } else if (input.endsWith("s")) {
      milliseconds = (long)(Double.parseDouble(input.replace("s", "")) * 1000);
    } else if (input.endsWith("m")) {
      milliseconds = (long)(Double.parseDouble(input.replace("m", "")) * 60 * 1000);
    } else if (input.endsWith("h")) {
      milliseconds = (long)(Double.parseDouble(input.replace("h", "")) * 60 * 60 * 1000);
    } else if (input.endsWith("d")) {
      milliseconds = (long)(Double.parseDouble(input.replace("d", "")) * 24 * 60 * 60 * 1000);
    } else {
      // fallback: assume milliseconds
      milliseconds = Long.parseLong(input.replaceAll("[^\\d]", ""));
    }
  }

  @Override
  public Object value() {
    return originalValue;
  }

  @Override
public String toString() {
  return "TimeDuration(" + milliseconds + " ms)";
}

  @Override
  public TokenType type() {
    return TokenType.TIME_DURATION;
  }

  public long getMilliseconds() {
    return milliseconds;
  }

  @Override
  public JsonElement toJson() {
    JsonObject object = new JsonObject();
    object.addProperty("type", "time_duration");
    object.addProperty("value", originalValue);
    object.addProperty("milliseconds", milliseconds);
    return object;
  }
}