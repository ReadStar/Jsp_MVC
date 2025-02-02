package vo;

public class ActionForward {
	/*
	 * 서블릿에서 클라이언트로부터 요청을 받아 처리 후
	 * View 페이지 등으로 포워딩 할 때
	 * 이동할 View 페이지의 URL(주소)과
	 * 포워딩 방식(Redirect or Dispatcher)을 다루기 위한 클래스
	 * 
	 */
	private String Path; //포워딩 할 View 페이지 URL을 저장
	private boolean isRedirect; //포워딩 방식 저장
	//true : Redirect, flase : Dispatcher
	public String getPath() {
		return Path;
	}
	public void setPath(String path) {
		Path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
}
