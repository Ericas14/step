// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.List;
import com.google.appengine.api.datastore.FetchOptions;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    private ArrayList<String> messages = new ArrayList<>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException { 
    Query query = new Query("Comment").addSort("Comment", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    String commentMax = request.getParameter("load-comments");
    int max = Integer.parseInt(commentMax);  
      List<String> comments = new ArrayList<>();
      for(Entity entity:results.asIterable(FetchOptions.Builder.withLimit(max)) ){
          String comment = (String)entity.getProperty("Comment");
          comments.add(comment);
      }
    response.setContentType("application/json"); 
    Gson gson = new Gson();
    response.getWriter().println(gson.toJson(comments));

    //String commentMax = request.getParameter("loadComments");
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String userComment = request.getParameter("user-comment");
      messages.add(userComment);
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      Entity commentEntity = new Entity("Comment");
      commentEntity.setProperty("Comment", userComment);
      datastore.put(commentEntity);
      response.sendRedirect("/index.html");
  }
}
