package Been;

public class User {
	public String getHash_id() {
		return hash_id;
	}
	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJianjie() {
		return jianjie;
	}
	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}
	public String getJieshao() {
		return jieshao;
	}
	public void setJieshao(String jieshao) {
		this.jieshao = jieshao;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBussiness() {
		return bussiness;
	}
	public void setBussiness(String bussiness) {
		this.bussiness = bussiness;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmployment() {
		return employment;
	}
	public void setEmployment(String employment) {
		this.employment = employment;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getEducation_extra() {
		return education_extra;
	}
	public void setEducation_extra(String education_extra) {
		this.education_extra = education_extra;
	}
	public int getFollowees() {
		return followees;
	}
	public void setFollowees(int followees) {
		this.followees = followees;
	}
	public int getFollowers() {
		return followers;
	}
	public void setFollowers(int followers) {
		this.followers = followers;
	}
	String hash_id;		//用户的hash_id
	String id;			//用户的id
	String avatar;		//用户的头像
	String name;		//用户的姓名
	String jianjie;		//用户的简介
	String jieshao;		//用户的介绍
	String location;	//居住地
	String bussiness;	//从事的行业
	String gender;		//性别
	String employment;	//所在公司
	String position;	//职位
	String education;	//大学
	String education_extra;	//专业
	int followees;		//关注了 人数
	int followers;		//关注者 人数

}
