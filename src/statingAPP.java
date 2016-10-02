import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class statingAPP {
	final static String appFolder="C:\\Users\\nabisawa\\Desktop\\aa";
	final static String GOOGLECHOROME_LINK_NAME = "chrome.lnk";
	final static String GOOGLECHOROME_BOOKMARK = "C:\\Users\\nabisawa\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Bookmarks";
	final static String GOOGLECHOROME_BOOKMARK_AUTO = "auto";
	
	public static void main(String args[]) throws IOException,
			InterruptedException {
		File[] appList = getFileList(appFolder);
		ProcessBuilder appPb = new ProcessBuilder();
		for (File app : appList) {
			//googlechorome���s��
			if (app.getName().equals(GOOGLECHOROME_LINK_NAME)) {
				//�Ώۂ̃t�H���_����u�b�N�}�[�N���擾
				ArrayList<String> googleBookMarkList = actionGooglechorome();
				//�ʏ�N��
				if(googleBookMarkList.isEmpty()){
					appPb.command(app.toString());
					Process appProcess = appPb.start();
					appProcess.waitFor();
				}else{
					//�u�b�N�}�[�N���N��
					for(String bookmarkWeb : googleBookMarkList){
						appPb.command("cmd","/c",app.toString(),bookmarkWeb);
						Process appProcess = appPb.start();
						appProcess.waitFor();
					}
				}
			}else{
				//�A�v���P�[�V�����N��
				appPb.command("cmd","/c",app.toString());
				Process appProcess = appPb.start();
				appProcess.waitFor();
			}
		}
	}

	// �w��t�H���_���̃t�@�C�����X�g���擾
	public static File[] getFileList(final String Folder) {
		File folder = new File(Folder);
		File[] fileList = folder.listFiles();

		return fileList;
	}

	// �w��t�H���_���̃u�b�N�}�[�N���擾
	public static ArrayList<String> actionGooglechorome() throws JsonProcessingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<String> list = new ArrayList<String>();
		JsonNode root = mapper.readTree(new File(GOOGLECHOROME_BOOKMARK));
		for(JsonNode i : root.get("roots")){
			for(JsonNode j : i.get("other")){
				if(GOOGLECHOROME_BOOKMARK_AUTO.equals(j.get("name").asText())){
					for(JsonNode k : j.get("children")){
						list.add(k.get("name").asText());
					}
				}
			}
		}
		return list;
	}
}
