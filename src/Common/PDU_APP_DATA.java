package Common;

import java.util.Arrays;

public class PDU_APP_DATA extends PDU_APP {
	private String nome;
	private byte[] song;
	private int blocos;
	private int numeration;
	private boolean complete;
	
	public PDU_APP_DATA(int version) {
		super(version);
		// TODO Auto-generated constructor stub
		this.nome=null;
		this.song=null;
		this.blocos=0;
		this.numeration=0;
		this.complete=false;
	}
	

	public PDU_APP_DATA(int version, String nome, byte[] song, int blocos, boolean complete, int numeration) {
		super(version);
		this.nome = nome;
		this.song = song;
		this.blocos = blocos;
		this.complete = complete;
		this.numeration=numeration;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public byte[] getSong() {
		return song;
	}


	public void setSong(byte[] song) {
		this.song = song;
	}


	public int getBlocos() {
		return blocos;
	}


	public void setBlocos(int blocos) {
		this.blocos = blocos;
	}


	public int getNumeration() {
		return numeration;
	}


	public void setNumeration(int numeration) {
		this.numeration = numeration;
	}


	public boolean isComplete() {
		return complete;
	}


	public void setComplete(boolean complete) {
		this.complete = complete;
	}


	@Override
	public String toString() {
		return "PDU_APP_DATA [nome=" + nome + ", song=" + Arrays.toString(song) + ", blocos=" + blocos + ", numeration="
				+ numeration + ", complete=" + complete + ", toString()=" + super.toString() + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + blocos;
		result = prime * result + (complete ? 1231 : 1237);
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + numeration;
		result = prime * result + Arrays.hashCode(song);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PDU_APP_DATA other = (PDU_APP_DATA) obj;
		if (blocos != other.blocos)
			return false;
		if (complete != other.complete)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (numeration != other.numeration)
			return false;
		if (!Arrays.equals(song, other.song))
			return false;
		return true;
	}



	
}
