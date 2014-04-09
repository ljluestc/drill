/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.drill;

import org.apache.drill.common.util.TestTools;
import org.apache.drill.exec.client.QuerySubmitter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

public class TestAltSortQueries {
  static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestAltSortQueries.class);
  
  @Rule public TestRule TIMEOUT = TestTools.getTimeoutRule(10000000);

  @Test
  public void testOrderBy() throws Exception{
    test("select R_REGIONKEY " +
         "from dfs.`[WORKING_PATH]/../../sample-data/region.parquet` " +
         "order by R_REGIONKEY");   
  }  

  @Test 
  public void testOrderBySingleFile() throws Exception{
    test("select R_REGIONKEY " +
         "from dfs.`[WORKING_PATH]/../../sample-data/regionsSF/` " +
         "order by R_REGIONKEY");   
  }  
    
  @Test
  public void testSelectWithLimit() throws Exception{
    test("select employee_id,  first_name, last_name from cp.`employee.json` order by employee_id limit 5 ");
  }

  @Test
  public void testSelectWithLimitOffset() throws Exception{
    test("select employee_id,  first_name, last_name from cp.`employee.json` order by employee_id limit 5 offset 10 ");
  }

  @Test
  public void testJoinWithLimit() throws Exception{
    test("SELECT\n" + 
        "  nations.N_NAME,\n" + 
        "  regions.R_NAME\n" + 
        "FROM\n" + 
        "  dfs.`[WORKING_PATH]/../../sample-data/nation.parquet` nations\n" + 
        "JOIN\n" + 
        "  dfs.`[WORKING_PATH]/../../sample-data/region.parquet` regions\n" + 
        "  on nations.N_REGIONKEY = regions.R_REGIONKEY" +
        " order by regions.R_NAME, nations.N_NAME " + 
        " limit 5");
  }
    
  
  private void test(String sql) throws Exception{
    boolean good = false;
    sql = sql.replace("[WORKING_PATH]", TestTools.getWorkingPath());
    
    try{
      QuerySubmitter s = new QuerySubmitter();
      s.submitQuery(null, sql, "sql", null, true, 1, "tsv");
      good = true;
    }finally{
      if(!good) Thread.sleep(2000);
    }
  }
  
}
