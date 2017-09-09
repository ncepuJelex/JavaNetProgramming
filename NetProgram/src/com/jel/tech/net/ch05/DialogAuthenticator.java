package com.jel.tech.net.ch05;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 * 使用http 的认证类 Authenticator,
 * 并利用Swing的dialog功能做界面供认证交互
 * @author jelex.xu
 * @date 2017年9月9日
 */
public class DialogAuthenticator extends Authenticator {
	//弹框认证窗口
	private JDialog authDialog;
	//框顶部显示
	private JLabel mainLabel = new JLabel("请输入用户名和密码：");

	//用户名输入框
	private JTextField usernameField = new JTextField(20);
	//密码输入框
	private JPasswordField passwordField = new JPasswordField(20);
	//确定
	private JButton okButton = new JButton("确定");
	//取消
	private JButton cancelButton = new JButton("取消");

	public DialogAuthenticator() {
		this("", new JFrame());
	}
	public DialogAuthenticator(String username) {
		this(username, new JFrame());
	}
	public DialogAuthenticator(JFrame parent) {
		this("", parent);
	}
	public DialogAuthenticator(String username, JFrame parent) {
		this.authDialog = new JDialog(parent, true); //owner and is modal
		Container pane = authDialog.getContentPane();
		pane.setLayout(new GridLayout(4, 1)); //4行，1列

		JLabel userLabel = new JLabel("用户名：");
		JLabel passwordLabel = new JLabel("密码：");

		pane.add(mainLabel); //row 1

		JPanel p2 = new JPanel();
		p2.add(userLabel);
		p2.add(usernameField);
		usernameField.setText(username);
		pane.add(p2); //row2

		JPanel p3 = new JPanel();
		p3.add(passwordLabel);
		p3.add(passwordField);
		pane.add(p3); //row 3

		JPanel p4 = new JPanel();
		p4.add(cancelButton);
		p4.add(okButton);
		pane.add(p4); //row 4

		authDialog.pack();

		ActionListener al = new OKResponse();
		okButton.addActionListener(al);
		usernameField.addActionListener(al);
		passwordField.addActionListener(al);

		cancelButton.addActionListener(new CancelResponse());
	}

	private void show() {
		String prompt = this.getRequestingPrompt();
		if(prompt == null) {
			String site = this.getRequestingSite().getHostName();
			String protocol = this.getRequestingProtocol();
			int port = this.getRequestingPort();
			if(site != null && protocol != null) {
				prompt = protocol + "//" + site;
				if(port > 0) prompt += ":" + port;
			}
			else {
				prompt = "";
			}
		}
		mainLabel.setText("请为 " + prompt + "输入用户名和密码：");
		authDialog.pack();
		authDialog.setVisible(true);
	}

	PasswordAuthentication response = null;

	class OKResponse implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			authDialog.setVisible(false); //隐藏dialog
			char[] password = passwordField.getPassword();
			String username = usernameField.getText();
			passwordField.setText(""); //清空密码栏
			response = new PasswordAuthentication(username, password);
		}
	}

	class CancelResponse implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			authDialog.setVisible(false);
			passwordField.setText("");
			response = null;
		}
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		this.show();
		return this.response;
	}

}
