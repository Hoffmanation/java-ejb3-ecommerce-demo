//package mystuff.web;
//
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.ExceptionMapper;
//import javax.ws.rs.ext.Provider;
//
//@Provider
//public class ErrorProvider implements ExceptionMapper<Exception>{
//
//
//	@Override
//	public Response toResponse(Exception e) {
//		return 
//				Response.serverError() // create response builder with Error code: 500
//				.entity(new Message("Server Error! unfortunately, the application cannot process your request at this time.")) // create a JSON within the reponse
//				.build(); // create the response using the builder
//	}
//
//}
