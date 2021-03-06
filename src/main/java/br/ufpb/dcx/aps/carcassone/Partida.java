package br.ufpb.dcx.aps.carcassone;

import java.util.ArrayList;

import br.ufpb.dcx.aps.carcassone.tabuleiro.TabuleiroFlexivel;
import br.ufpb.dcx.aps.carcassone.tabuleiro.Tile;

public class Partida {

	private BolsaDeTiles tiles;
	private Tile proximoTile;
	private int jogadorAtual;
	private TabuleiroFlexivel tabuleiro = new TabuleiroFlexivel("  ");
	private String statusPartida;
	private String statusTurno= "Tile_Posicionado";
	private ArrayList<Jogador> jogadores = new ArrayList<>();
	private int turnos =0;
	private boolean tilePosicionado;

	Partida(BolsaDeTiles tiles, Cor ...sequencia) {
		this.tiles = tiles;
		pegarProximoTile();
		this.statusPartida = "Em_Andamento";
		for(int k=0; k< sequencia.length; k++) {
			Jogador jogador = new Jogador(sequencia[k]);
			jogadores.add(jogador);
			}
	}

	public String relatorioPartida() {
		String relatorio = "Status: "+this.statusPartida +"\nJogadores: " ;
		for(int k=0; k<jogadores.size(); k++) {
			relatorio += jogadores.get(k).toString();
			if(k<jogadores.size()-1) {	
				relatorio+="; ";
			}
		}
		return relatorio;
	}


	public String relatorioTurno() {
		if(this.statusPartida.equals("Partida_Finalizada")) {
			throw new ExcecaoJogo("Partida finalizada");
		}
		return "Jogador: "+jogadores.get(this.jogadorAtual).getCor()+
				"\nTile: "+proximoTile.getId()+proximoTile.getOrientacao().getAbreviacao()+"\nStatus: "+this.statusTurno;
		
	}
	public Partida girarTile() {
		if (this.statusPartida.equals("Partida_Finalizada")) {
			throw new ExcecaoJogo("Não pode girar tiles com a partida finalizada");
		}

		if (tilePosicionado) {
			throw new ExcecaoJogo("Não pode girar tile já posicionado");
		}

		proximoTile.girar();
		return this;
	
	}

	private void pegarProximoTile() {
		proximoTile = tiles.pegar();
		turnos+=1;
		if(turnos==1) {
			this.tabuleiro.adicionarPrimeiroTile(proximoTile);
			tilePosicionado=true;
		}
		if(proximoTile==null) {
			this.finalizarPartida();
		}
		else {
		 proximoTile.reset();
		}
	}

	public Partida finalizarTurno() {
		if(this.jogadorAtual==this.jogadores.size()-1) {	
			this.jogadorAtual=0;
			}
		else {
			this.jogadorAtual+=1;
		}
		this.pegarProximoTile();
		tilePosicionado=false;
		this.statusTurno="Início_Turno";
		return this;
		}
	public Partida finalizarPartida() {
		this.statusPartida = "Partida_Finalizada";
		return this;
	}
	

	public Partida posicionarTile(Tile tileReferencia, Lado ladoTileReferencia) {
		tabuleiro.posicionar(tileReferencia, ladoTileReferencia, proximoTile);
		tilePosicionado=true;
		this.statusTurno="Tile_Posicionado";
		return this;
	}

	public Partida posicionarMeepleEstrada(Lado lado) {
		return this;
	}

	public Partida posicionarMeepleCampo(Vertice vertice) {
		return this;
	}

	public Partida posicionarMeepleCidade(Lado lado) {
		return this;
	}

	public Partida posicionarMeepleMosteiro() {
		return this;
	}

	public String getEstradas() {
		return null;
	}

	public String getCampos() {
		return null;
	}

	public String getCidades() {
		return null;
	}

	public String getMosteiros() {
		return null;
	}

	public String relatorioTabuleiro() {
		return tabuleiro.toString();
	
	}
}
