package entity;

public class Mensuality {

	public Mensuality(String id,String nom, String prenom, String montant, String date, String mois, String paiement,String type, String classe) {
		super();
		this.id=id;
		this.nom = nom;
		this.prenom = prenom;
		this.montant = montant;
		Date = date;
		this.mois = mois;
		this.paiement = paiement;
		this.type = type;
		this.classe = classe;
	}

	
    public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	
    private String id;
	private String type;public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}

	private String nom;
	private String prenom;
	private String montant;
	private String Date;
	private String mois;
	private String paiement;
	private String classe;
	public Mensuality() {}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMontant() {
		return montant;
	}

	public void setMontant(String montant) {
		this.montant = montant;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getMois() {
		return mois;
	}

	public void setMois(String mois) {
		this.mois = mois;
	}

	public String getPaiement() {
		return paiement;
	}

	public void setPaiement(String paiement) {
		this.paiement = paiement;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
