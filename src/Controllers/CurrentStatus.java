package Controllers;

public final class CurrentStatus {

    private final static CurrentStatus INSTANCE = new CurrentStatus();
    private String id;
    boolean idLogin = true;
    private AdminController admin;
    private MemberController member;

    private CurrentStatus() {}

    public void setID(String id)
    {
        this.id = id;
    }

    public String getID()
    {
        return this.id;
    }

    public static CurrentStatus getINSTANCE()
    {
        return INSTANCE;
    }

	public AdminController getAdmin() {
		return admin;
	}

	public void setAdmin(AdminController admin) {
		this.admin = admin;
	}

	public MemberController getMember() {
		return member;
	}

	public void setMember(MemberController member) {
		this.member = member;
	}
	
}