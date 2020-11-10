package vo;

public class ActionForward {
	//포워딩 정보(포워딩할 주소, 포워딩 방식)를 관리하는 클래스
	private String path; //포워딩 주소
	private boolean isRedirect; //포워딩 방식(true : Redirect, flase : Dispatcher)
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
}
