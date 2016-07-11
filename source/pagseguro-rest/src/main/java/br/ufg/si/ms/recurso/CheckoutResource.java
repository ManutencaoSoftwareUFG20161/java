package br.ufg.si.ms.recurso;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("checkout")
public class CheckoutResource {


	@PUT
	public Response realizarCheckout() {

		return Response.status(Status.BAD_REQUEST).build();
	}
}
