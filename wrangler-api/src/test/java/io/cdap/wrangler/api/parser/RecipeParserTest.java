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

import org.junit.Assert;
import org.junit.Test;

/**
 * Custom test to validate ByteSize and TimeDuration functionality.
 */
public class RecipeParserTest {

  @Test
  public void testByteSizeParsing() {
    ByteSize size = new ByteSize("1.5MB");
    Assert.assertEquals(1572864L, size.getBytes());

    ByteSize size2 = new ByteSize("1024");
    Assert.assertEquals(1024L, size2.getBytes());

    ByteSize size3 = new ByteSize("2GB");
    Assert.assertEquals(2147483648L, size3.getBytes());
  }

  @Test
  public void testTimeDurationParsing() {
    TimeDuration duration1 = new TimeDuration("2m");
    Assert.assertEquals(120000L, duration1.getMilliseconds());

    TimeDuration duration2 = new TimeDuration("1.5h");
    Assert.assertEquals(5400000L, duration2.getMilliseconds());

    TimeDuration duration3 = new TimeDuration("100ms");
    Assert.assertEquals(100L, duration3.getMilliseconds());
  }
}