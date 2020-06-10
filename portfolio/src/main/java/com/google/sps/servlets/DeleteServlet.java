import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/delete-data")
public class DeleteServlet extends HttpServlet{

  /*Queries the datastore, gets the keys of all of the comments in the
  datastore and puts them into an list, then the datastore deletes all
   keys. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
      Query query = new Query("Comment").addSort("Comment", SortDirection.DESCENDING);
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      PreparedQuery results = datastore.prepare(query);
  
      List<Key> comments = new ArrayList<>();
      for(Entity entity:results.asIterable() ){
          Key comment = entity.getKey();
          comments.add(comment);
      }
      datastore.delete(comments);
      
  } 
}