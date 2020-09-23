package homebook3;
// 계정과목 vo
public class AccountTitle {
	private String titleid;
	private String title;
	
	public String getTitleid() {
		return titleid;
	}
	public void setTitleid(String titleid) {
		this.titleid = titleid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return titleid ;
	}
}