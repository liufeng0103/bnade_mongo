package com.bnade.wow.rs;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bnade.wow.service.PetService;
import com.bnade.wow.service.impl.PetServiceImpl;

@Path("/pet")
public class PetResource {
	
	private PetService petService;
	
	public PetResource() {
		petService = new PetServiceImpl();	
	}
	
	/*
	 * 通过宠物名查询宠物信息
	 */
	@GET
	@Path("/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getPetsByName(@PathParam("name")String name) {
		try {
			return petService.getPetsByName(name);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
	}
}
