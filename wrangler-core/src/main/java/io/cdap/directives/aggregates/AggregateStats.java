/*
 *  Copyright © 2017-2019 Cask Data, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */
package io.cdap.directives.aggregates;

import java.util.ArrayList;
import java.util.List;

import io.cdap.wrangler.api.Arguments;
import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.DirectiveExecutionException;
import io.cdap.wrangler.api.DirectiveParseException;
import io.cdap.wrangler.api.ExecutorContext;
import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.ColumnName;
import io.cdap.wrangler.api.parser.TimeDuration;
import io.cdap.wrangler.api.parser.TokenType;
import io.cdap.wrangler.api.parser.UsageDefinition;

/**
 * A directive to aggregate total size and time from ByteSize and TimeDuration columns.
 */
public class AggregateStats implements Directive {

  public static final String NAME = "aggregate-stats";

  private String sizeColumn;
  private String durationColumn;
  private String outputSizeColumn;
  private String outputTimeColumn;

  @Override
  public void initialize(Arguments arguments) throws DirectiveParseException {
    sizeColumn = ((ColumnName) arguments.value("sizeColumn")).value();
    durationColumn = ((ColumnName) arguments.value("durationColumn")).value();
    outputSizeColumn = ((ColumnName) arguments.value("outputSizeColumn")).value();
    outputTimeColumn = ((ColumnName) arguments.value("outputTimeColumn")).value();
  }

 @Override
public UsageDefinition define() {
  UsageDefinition.Builder builder = UsageDefinition.builder(NAME);
  builder.define("sizeColumn", TokenType.COLUMN_NAME);
  builder.define("durationColumn", TokenType.COLUMN_NAME);
  builder.define("outputSizeColumn", TokenType.COLUMN_NAME);
  builder.define("outputTimeColumn", TokenType.COLUMN_NAME);
  return builder.build();
}

  @Override
  public List<Row> execute(List<Row> rows, ExecutorContext context) throws DirectiveExecutionException {
    long totalBytes = 0;
    long totalMilliseconds = 0;

    for (Row row : rows) {
      Object sizeVal = row.getValue(sizeColumn);
      Object timeVal = row.getValue(durationColumn);

      if (sizeVal instanceof String) {
        totalBytes += new ByteSize((String) sizeVal).getBytes();
      }

      if (timeVal instanceof String) {
        totalMilliseconds += new TimeDuration((String) timeVal).getMilliseconds();
      }
    }

    List<Row> result = new ArrayList<>();
    Row output = new Row();

    output.add(outputSizeColumn, (double) totalBytes / (1024 * 1024)); // MB
    output.add(outputTimeColumn, (double) totalMilliseconds / 1000);   // sec

    result.add(output);
    return result;
  }

  @Override
  public void destroy() {
    // No cleanup needed
  }
}