
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Interface extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JButton scan = null;
	JButton count = null;

	JTextField textField = null;

	void MainInterface() {
		this.setTitle("WC");
		FlowLayout layout = new FlowLayout();// ����
		JLabel label = new JLabel("��ѡ���ļ���");// ��ǩ
		textField = new JTextField(30);// �ı���
		scan = new JButton("���");// ���
		count = new JButton("ͳ��");// ͳ��

		// ���ò���
		layout.setAlignment(FlowLayout.LEFT);// �����
		this.setLayout(layout);
		this.setBounds(400, 200, 600, 300);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        scan.addActionListener(this);
//        count.addActionListener(this);
		scan.addActionListener(new MyActionListener());
		count.addActionListener(new MyActionListener());
		this.add(label);
		this.add(textField);
		this.add(scan);
		this.add(count);

	}

	class MyActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
//			String find = seekText.getText();
			if (e.getActionCommand() == "���") {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.showDialog(new JLabel(), "ѡ��");
				File file = chooser.getSelectedFile();
				textField.setText(file.getAbsoluteFile().toString());
			}
			String path = textField.getText();
			if (e.getActionCommand() == "ͳ��") {

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

					JOptionPane.showMessageDialog(null, CountChar(LongString) + "\n" + "������" + countLine + "\n"
							+ CountWord(LongString) + "\n" + Special(path));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					try {
						br.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		}

		String CountChar(String LongString) {
			int countChar = 0;
			
			for (int i = 0; i < LongString.split(" ").length; i++) {
				countChar += LongString.split(" ")[i].length();
			}
//			System.out.println("�ַ�����" + countChar);
			String returnstring = "�ַ�����" + countChar;
			return returnstring;
		}

		String CountWord(String LongString) {
			int countWord = 0;
			
			for (int i = 0; i < LongString.split(" ").length; i++) {
				if (!LongString.split(" ")[i].equals("") && (LongString.split(" ")[i].matches("^[a-zA-Z]*$"))) {
					countWord++;
				}
			}
//			System.out.println("��������" + countWord);
			String returnstring = "��������" + countWord;
			return returnstring;
		}

		String Special(String path) throws IOException {
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
			countSpecialLine = "����: " + blankLine + "\n" + "������: " + codeLine + "\n" + "ע���� : " + Annotation + "\n";
//			System.out.println(countSpecialLine);
			br.close();
			return countSpecialLine;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
