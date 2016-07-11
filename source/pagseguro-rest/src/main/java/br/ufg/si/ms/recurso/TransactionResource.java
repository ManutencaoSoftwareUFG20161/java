package br.ufg.si.ms.recurso;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.com.uol.pagseguro.service.TransactionSearchService;

@Path("/transaction")
public class TransactionResource {
	@GET
	@Path("/{codigo}")
	public Response findByCode(@PathParam("codigo") String codigo) {

		Transaction transaction = null;

		try {
			transaction = TransactionSearchService.searchByCode(PagSeguroConfig.getAccountCredentials(),
				codigo);

			Gson gson = new Gson();
			String json = gson.toJson(transaction, Transaction.class);

			return Response.status(Status.ACCEPTED).entity(json).build();

		} catch (PagSeguroServiceException e) {
			System.err.println(e.getMessage());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
}
