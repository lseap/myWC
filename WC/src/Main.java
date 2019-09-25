import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {

		try {
			Scanner sc = new Scanner(System.in);
			String console = sc.nextLine();
			String ConsoleText[] = console.split(" ");
			String param = ConsoleText[0];
			String path;
			if (ConsoleText.length == 1) {
				path = null;
			} else
				path = ConsoleText[1];
			Interface MainInterface = new Interface();

			switch (param) {
			case "-c":
			case "-w":
			case "-l":
				Basic(param, path);
				break;
			case "-a":
				Special(path);
				break;
			case "-x":
				MainInterface.MainInterface();
				break;
			default:
				System.out.println("输入错误");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void Basic(String param, String path) throws IOException {

		BufferedReader br = null;
		String s = "";
		String LongString = "";
		int countLine = 0;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			while ((s = br.readLine()) != null) {
				s = s + " ";
				LongString += s;
				countLine++;
			}

			switch (param) {
			case "-c":
				CountChar(LongString);
				break;
			case "-w":
				CountWord(LongString);
				break;
			case "-l":
				System.out.println("行数：" + countLine);
				break;
			default:
				System.out.println("输入错误");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			br.close();
		}
	}

	static void CountChar(String LongString) {
		int countChar = 0;
		for (int i = 0; i < LongString.split(" ").length; i++) {
			countChar += LongString.split(" ")[i].length();
		}
		System.out.println("字符数：" + countChar);
	}

	static void CountWord(String LongString) {
		int countWord = 0;
		for (int i = 0; i < LongString.split(" ").length; i++) {
			if (!LongString.split(" ")[i].equals("") && (LongString.split(" ")[i].matches("^[a-zA-Z]*$"))) {
				countWord++;
			}
		}
		System.out.println("单词数：" + countWord);
	}

	static void Special(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		String countSpecialLine = "";
		String s;
		int blankLine = 0;
		int codeLine = 0;
		int Annotation = 0;
		Boolean flag = false;

		while ((s = br.readLine()) != null) {
			s = s.trim();
			if (s.contains("/*")) {
				Annotation++;
				flag = true;
				if (!s.startsWith("/*")) {
					codeLine++;
				}
			} else if (flag == true) {
				Annotation++;
				if (s.contains("*/")) {
					flag = false;
					if (!s.startsWith("*/")) {
						codeLine++;
					}
				}
			} else if (s.contains("//")) {
				Annotation++;
				if (!s.startsWith("//")) {
					codeLine++;
				}
			} else if (s.matches("\\s*(\\{|\\})?")) {
				blankLine++;
			} else
				codeLine++;
		}
		countSpecialLine = "空行: " + blankLine + "\n" + "代码行: " + codeLine + "\n" + "注释行 : " + Annotation + "\n";
		System.out.println(countSpecialLine);
		br.close();
	}

}
