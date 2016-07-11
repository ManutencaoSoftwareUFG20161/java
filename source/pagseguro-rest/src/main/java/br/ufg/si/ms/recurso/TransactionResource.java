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
import br.com.uol.pagseguro.service.NotificationService;
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

	@GET
	@Path("/notification/{codigo}")
	public Response findByNotification(@PathParam("codigo") String codigo) {
		Transaction transaction = null;

		try {
			transaction = NotificationService.checkTransaction(PagSeguroConfig.getAccountCredentials(),
				codigo);

			Gson gson = new Gson();
			String json = gson.toJson(transaction, Transaction.class);

			return Response.status(Status.OK).entity(json).build();

		} catch (PagSeguroServiceException e) {
			System.err.println(e.getMessage());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	/**
     * O parametrodata deve ser o valor Long da data em formato String
     * @param dataInicial
     * @param dataFinal
     * @return
     */
    @GET
    @Path("/{dataInicial}/{dataFinal}")
    public Response searchByDate(@PathParam("dataInicial") String dataInicial,
   							 @PathParam("dataFinal") String dataFinal) {

   	 Date initialDate = new Date(new Long(dataInicial));
   	 Date finalDate = new Date(new Long(dataFinal));

   	 Integer page = Integer.valueOf(1);

    	Integer maxPageResults = Integer.valueOf(10);

    	TransactionSearchResult transactionSearchResult = null;

    	try {
   		 transactionSearchResult =TransactionSearchService.searchByDate(PagSeguroConfig.getAccountCredentials(),
   		     	initialDate, finalDate, page, maxPageResults);

   		 Gson gson = new Gson();
   		 String resposta = gson.toJson(transactionSearchResult,TransactionSearchResult.class);

   		 return Response.status(Status.OK).entity(resposta).build();

   	 } catch (PagSeguroServiceException e) {
   		 System.err.println("Deu problema na requisição");

   		 return Response.status(Status.BAD_REQUEST).build();
   	 }


    }

}
