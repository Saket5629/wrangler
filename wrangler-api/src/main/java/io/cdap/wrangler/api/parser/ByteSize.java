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
 * ByteSize Token - parses string like "10MB" and gives bytes.
 */
public class ByteSize implements Token {
  private final String originalValue;
  private final long bytes;

  public ByteSize(String value) {
    this.originalValue = value;

    String input = value.trim().toLowerCase();

    if (input.endsWith("kb")) {
      bytes = (long)(Double.parseDouble(input.replace("kb", "")) * 1024);
    } else if (input.endsWith("mb")) {
      bytes = (long)(Double.parseDouble(input.replace("mb", "")) * 1024 * 1024);
    } else if (input.endsWith("gb")) {
      bytes = (long)(Double.parseDouble(input.replace("gb", "")) * 1024 * 1024 * 1024);
    } else if (input.endsWith("b")) {
      bytes = (long)(Double.parseDouble(input.replace("b", "")));
    } else {
      // fallback: assume it's already in bytes
      bytes = Long.parseLong(input.replaceAll("[^\\d]", ""));
    }
  }

  @Override
  public Object value() {
    return originalValue;
  }

  @Override
  public TokenType type() {
    return TokenType.BYTE_SIZE;
  }

  public long getBytes() {
    return bytes;
  }

  @Override
public String toString() {
  return "ByteSize(" + bytes + " bytes)";
}

  @Override
  public JsonElement toJson() {
    JsonObject object = new JsonObject();
    object.addProperty("type", "byte_size");
    object.addProperty("value", originalValue);
    object.addProperty("bytes", bytes);
    return object;
  }
}