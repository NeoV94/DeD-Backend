package com.howtodoinjava.jersey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("")
public class JerseyService {

	@GET
	public String getMsg() {
		return "Hello World !! - Jersey 2";
	}

	@Path("/Players")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized Response getPlayerData() {
		List<Player> listaUser = new ArrayList<Player>();
		try {
			System.setProperty("try", "prova");
			Connection con = new DBconnection().getCon();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from personaggi order by iniziativa desc,destrezza desc");
			while (rs.next()) {
				Player user = new Player();
				user.setNome(rs.getString(1));
				user.setForza(rs.getInt(2));
				user.setDestrezza(rs.getInt(3));
				user.setCostituzione(rs.getInt(4));
				user.setIntelligenza(rs.getInt(5));
				user.setSaggezza(rs.getInt(6));
				user.setCarisma(rs.getInt(7));
				user.setIniziativa(rs.getInt(8));
				listaUser.add(user);
			}
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		GenericEntity<List<Player>> entity = new GenericEntity<List<Player>>(listaUser) {
		};
		return Response.ok(entity).header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600").build();
	}

	@Path("/InserisciPlayer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public synchronized Response setPlayer(Player player) {
		int results = 0;
		try {
			Connection con = new DBconnection().getCon();
			String sql = "insert into personaggi(nome,destrezza,iniziativa) values (?,?,?)";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, player.getNome());
			st.setInt(2, player.getDestrezza());
			st.setInt(3, player.getIniziativa());
			results = st.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (results > 0) {
			synchronized (System.getProperty("try")) {
				System.getProperty("try").notifyAll();
			}
			return Response.ok().header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600").build();
		} else {
			return Response.status(400).header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600").build();
		}
	}

	@Path("resetIniziative")
	@GET
	public synchronized Response resetIniziative() {
		try {
			Connection con = new DBconnection().getCon();
			Statement st = con.createStatement();
			st.executeUpdate("update personaggi set Iniziativa=0");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600").build();
		}
		synchronized (System.getProperty("try")) {
			System.getProperty("try").notifyAll();
		}
		return Response.ok().header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600").build();
	}

	@Path("setIniziativa/{nome}")
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	public synchronized Response setIniziative(int iniziativa, @PathParam("nome") String nome) {
		try {
			Connection con = new DBconnection().getCon();
			String sql = "update personaggi set Iniziativa=? where nome=?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, iniziativa);
			st.setString(2, nome);
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600").build();
		}
		synchronized (System.getProperty("try")) {
			System.getProperty("try").notifyAll();
		}
		return Response.ok().header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600").build();
	}

	@Path("deletePlayer/{nome}")
	@DELETE
	public synchronized Response deletePlayer(@PathParam("nome") String nome) {
		try {
			Connection con = new DBconnection().getCon();
			String sql = "delete from personaggi where nome=?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, nome);
			st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600").build();
		}
		synchronized (System.getProperty("try")) {
			System.getProperty("try").notifyAll();
		}
		return Response.ok().header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600").build();
	}

	@Path("/Syncro")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response syncro() {
		try {
			if (System.getProperty("try") == null) {
				System.setProperty("try", "prova");
			}
			synchronized (System.getProperty("try")) {
				System.getProperty("try").wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getPlayerData();
	}

	@Path("/SyncroPosizione")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response syncroPosizione() {
		try {
			if (System.getProperty("try") == null) {
				System.setProperty("try", "prova");
			}
			synchronized (System.getProperty("try")) {
				System.getProperty("try").wait();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getPosizioni();
	}

	@Path("/puntiFerita/{nome}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPuntiFerita(@PathParam("nome") String nome) {
		PlayerHp player = new PlayerHp();
		try {
			Connection conn = new DBconnection().getCon();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from hp where nome='" + nome + "'");
			while (rs.next()) {
				player.setNome(rs.getString(1));
				player.setHp_max(rs.getInt(2));
				player.setHp_attuali(rs.getInt(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600").build();
		}
		return Response.ok(player).header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600").build();

	}

	@Path("/ferisci/{nome}")
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	public Response getPuntiFerita(int danni, @PathParam("nome") String nome) {
		try {
			Connection conn = new DBconnection().getCon();
			PreparedStatement pst = conn
					.prepareStatement("update hp set punti_ferita_attuali=punti_ferita_attuali-? where nome=?");
			pst.setInt(1, danni);
			pst.setString(2, nome);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600").build();
		}
		synchronized (System.getProperty("try")) {
			System.getProperty("try").notifyAll();
		}
		return Response.ok().header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600").build();

	}

	@Path("/cura/{nome}")
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	public Response cura(int cure, @PathParam("nome") String nome) {
		try {
			PlayerHp player = (PlayerHp) getPuntiFerita(nome).getEntity();
			player.setHp_attuali(player.getHp_attuali() + cure);
			if (player.getHp_attuali() > player.getHp_max()) {
				player.setHp_attuali(player.getHp_max());
			}
			Connection conn = new DBconnection().getCon();
			PreparedStatement pst = conn.prepareStatement("update hp set punti_ferita_attuali=? where nome=?");
			pst.setInt(1, player.getHp_attuali());
			pst.setString(2, nome);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600").build();
		}
		synchronized (System.getProperty("try")) {
			System.getProperty("try").notifyAll();
		}
		return Response.ok().header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600").build();
	}

	@Path("/posizione/{nome}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPosizione(@PathParam("nome") String nome) {
		Griglia griglia = new Griglia();
		try {
			Connection conn = new DBconnection().getCon();
			PreparedStatement pst = conn.prepareStatement("select * from griglia where nome=?");
			pst.setString(1, nome);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				griglia.setNome(rs.getString(1));
				griglia.setX(rs.getInt(2));
				griglia.setY(rs.getInt(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600").build();
		}
		return Response.ok(griglia).header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600").build();
	}

	@Path("/posizioni")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPosizioni() {
		List<Griglia> mappa = new ArrayList<Griglia>();
		try {
			Connection conn = new DBconnection().getCon();
			PreparedStatement pst = conn.prepareStatement("select * from griglia");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Griglia griglia = new Griglia();
				griglia.setNome(rs.getString(1));
				griglia.setX(rs.getInt(2));
				griglia.setY(rs.getInt(3));
				mappa.add(griglia);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600").build();
		}
		return Response.ok(mappa).header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600").build();
	}

	@Path("/movePg")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response movePg(Griglia griglia) {
		try {
			Connection conn = new DBconnection().getCon();
			PreparedStatement pst = conn.prepareStatement("insert into griglia(nome,x,y) values (?,?,?) on duplicate key update x=?,y=?");
			pst.setString(1, griglia.getNome());
			pst.setInt(2, griglia.getX());
			pst.setInt(3, griglia.getY());
			pst.setInt(4, griglia.getX());
			pst.setInt(5, griglia.getY());
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).header("Access-Control-Allow-Origin", "*")
		            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
		            .header("Access-Control-Allow-Credentials", "true")
		            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
		            .header("Access-Control-Max-Age", "1209600").build();
		}
		synchronized (System.getProperty("try")) {
			System.getProperty("try").notifyAll();
		}
		return Response.ok().header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600").build();
	}
}
