package TableStyles;

public class MembersTable {
	public final int memberUID;
	public final String memberName;
	public final String memberPassword;
	public final String memberAddress;
	public final String memberPhone;
	public final String memberEmail;
	public final String memberJoined;
	
	public MembersTable(Integer uid, String name, String password, String address, String phone, String email, String joined)
	{
		this.memberUID = uid;
		this.memberName = name;
		this.memberPassword = password;
		this.memberAddress = address;
		this.memberPhone = phone;
		this.memberEmail = email;
		this.memberJoined = joined;
	}

	public int getMemberUID() {
		return memberUID;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public String getMemberJoined() {
		return memberJoined;
	}
	
	
}
