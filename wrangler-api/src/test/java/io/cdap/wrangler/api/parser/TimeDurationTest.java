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

public class TimeDurationTest {

  @Test
  public void testSecondsConversion() {
    TimeDuration t = new TimeDuration("2s");
    Assert.assertEquals(2000, t.getMilliseconds());
  }

  @Test
  public void testMinutesConversion() {
    TimeDuration t = new TimeDuration("3m");
    Assert.assertEquals(180000, t.getMilliseconds());
  }

  @Test
  public void testMillisecondsConversion() {
    TimeDuration t = new TimeDuration("150ms");
    Assert.assertEquals(150, t.getMilliseconds());
  }

  @Test
  public void testHoursConversion() {
    TimeDuration t = new TimeDuration("1.5h");
    Assert.assertEquals(5400000, t.getMilliseconds());
  }
}