package Common;

public class PDU_APP_REG_RESP extends PDU_APP{

	private int mensagem;
	private int tipo;
	public PDU_APP_REG_RESP(int version) {
		super(version);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "PDU_APP_REG_RESP [mensagem=" + mensagem + ", tipo=" + tipo + ", toString()=" + super.toString() + "]";
	}

	public PDU_APP_REG_RESP(int version, int mensagem,int tipo) {
		super(version);
		this.mensagem = mensagem;
		this.tipo=tipo;
	}
	public int getMensagem() {
		return mensagem;
	}
	public void setMensagem(int mensagem) {
		this.mensagem = mensagem;
	}

}
