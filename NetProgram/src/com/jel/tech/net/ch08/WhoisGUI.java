package com.jel.tech.net.ch08;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

/**
 * Whois用户界面，结合Whois（网络连接查询）一起使用
 * @author jelex.xu
 * @date 2017年9月13日
 */
public class WhoisGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	//顶上的搜索输入框
	private JTextField searchString = new JTextField(30);
	//最下面的结果展示文本区域
	private JTextArea names = new JTextArea(15, 80);
	//最上面右方的查找按钮
	private JButton findButton = new JButton("Find");
	//查找的类别组
	private ButtonGroup searchIn = new ButtonGroup();
	//查找的项
	private ButtonGroup searchFor = new ButtonGroup();
	//查找是否完全匹配
	private JCheckBox exactMatch = new JCheckBox("Exact Match");
	//提供服务的服务器地址
	private JTextField chosenServer = new JTextField();
	//和服务器交互的工具
	private Whois server;

	public WhoisGUI(Whois whois) {
		super("Whois"); //设置标题并初始化JFrame
		this.server = whois;
		Container pane = this.getContentPane();

		Font f = new Font("Courier New", Font.PLAIN, 12);
		names.setFont(f); //设置结果展示的字体，可惜看不到了！因为连不通
		names.setEditable(false); //结果区域不可编辑
		/*
		 * 把结果区域放到scroll pane中可滚动
		 */
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1,1,10,10));
		JScrollPane jsp = new JScrollPane(names);
		centerPanel.add(jsp);
		pane.add("Center", centerPanel);

		// You don't want the buttons in the south and north
		// to fill the entire sections so add Panels there
		// and use FlowLayouts in the Panel
		/*
		 * 最顶上一行
		 */
		JPanel northPanel = new JPanel();
		JPanel northPanelTop = new JPanel();
		northPanelTop.setLayout(new FlowLayout(FlowLayout.LEFT));
		northPanelTop.add(new JLabel("Whois: "));
		northPanelTop.add("North", searchString);
		northPanelTop.add(exactMatch);
		northPanelTop.add(findButton);
		/*
		 * 最上面是2行1列
		 */
		northPanel.setLayout(new BorderLayout(2,1));
		//最上面一行在北
		northPanel.add("North", northPanelTop);
		//第2行是1行3列，上下左右间隙为5
		JPanel northPanelBottom = new JPanel();
		northPanelBottom.setLayout(new GridLayout(1,3,5,5));
		//初始化查找的项
		northPanelBottom.add(initRecordType());
		//初始化查找类别
		northPanelBottom.add(initSearchFields());
		//初始化服务器输入选项
		northPanelBottom.add(initServerChoice());
		//居中
		northPanel.add("Center", northPanelBottom);
		//对应 center位置的结果展示区域框，放在结果上面
		pane.add("North", northPanel);
		/*
		 * 查找按钮和查找输入框的监听事件
		 */
		ActionListener al = new LookupNames();
		findButton.addActionListener(al);
		searchString.addActionListener(al);
	}

	private JPanel initRecordType() {
		JPanel p = new JPanel();
		//6行2列，水平间隙为5，垂直间隙为2
		p.setLayout(new GridLayout(6, 2, 5, 2));
		p.add(new JLabel("Search for:"));
		p.add(new JLabel(""));
		//第一个选项默认选中状态
		JRadioButton any = new JRadioButton("Any", true);
		any.setActionCommand("Any");
		searchFor.add(any);
		p.add(any);
		p.add(this.makeRadioButton("Network"));
		p.add(this.makeRadioButton("Person"));
		p.add(this.makeRadioButton("Host"));
		p.add(this.makeRadioButton("Domain"));
		p.add(this.makeRadioButton("Organization"));
		p.add(this.makeRadioButton("Group"));
		p.add(this.makeRadioButton("Gateway"));
		p.add(this.makeRadioButton("ASN"));
		return p;
	}
	private JRadioButton makeRadioButton(String label) {
		JRadioButton button = new JRadioButton(label, false);
		button.setActionCommand(label);
		searchFor.add(button);
		return button;
	}

	private JPanel initSearchFields() {
		JPanel p = new JPanel();
		//虽然只有5行，但为了保持格式一致，写作6行
		p.setLayout(new GridLayout(6, 1, 5, 2));
		p.add(new JLabel("Search In: "));
		JRadioButton all = new JRadioButton("All", true);
		all.setActionCommand("All");
		searchIn.add(all);
		p.add(all);
		p.add(this.makeSearchInRadioButton("Name"));
		p.add(this.makeSearchInRadioButton("Mailbox"));
		p.add(this.makeSearchInRadioButton("Handle"));
		return p;
	}
	private JRadioButton makeSearchInRadioButton(String label) {
		JRadioButton button = new JRadioButton(label, false);
		button.setActionCommand(label);
		searchIn.add(button);
		return button;
	}

	private JPanel initServerChoice() {
		final JPanel p = new JPanel();
		p.setLayout(new GridLayout(6, 1, 5, 2));
		p.add(new JLabel("Search At: "));
		chosenServer.setText(server.getHost().getHostName());
		p.add(chosenServer);
		chosenServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					server = new Whois(chosenServer.getText());
				} catch (UnknownHostException ex) {
					JOptionPane.showMessageDialog(p, ex.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		return p;
	}

	/*
	 * 这里写得有点意思啊！
	 */
	private class LookupNames implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			names.setText(""); //清空之前的结果
			/*
			 * An abstract class to perform lengthy GUI-interaction
			 * tasks in a background thread. Several background threads
			 * can be used to execute such tasks. However, the exact
			 *  strategy of choosing a thread for any particular
			 *  SwingWorker is unspecified and should not be relied on.
			 */
			SwingWorker<String, Object> worker = new Lookup();
			worker.execute();
		}
	}

	private class Lookup extends SwingWorker<String, Object> {
		@Override
		protected String doInBackground() throws Exception {
			Whois.SearchIn group = Whois.SearchIn.ALL;
			Whois.SearchFor category = Whois.SearchFor.ANY;
			String searchForLabel = searchFor.getSelection().getActionCommand();
			String searchInLabel = searchIn.getSelection().getActionCommand();
			if (searchInLabel.equals("Name"))
				group = Whois.SearchIn.NAME;
			else if (searchInLabel.equals("Mailbox")) {
				group = Whois.SearchIn.MAILBOX;
			} else if (searchInLabel.equals("Handle")) {
				group = Whois.SearchIn.HANDLE;
			}
			if (searchForLabel.equals("Network")) {
				category = Whois.SearchFor.NETWORK;
			} else if (searchForLabel.equals("Person")) {
				category = Whois.SearchFor.PERSON;
			} else if (searchForLabel.equals("Host")) {
				category = Whois.SearchFor.HOST;
			} else if (searchForLabel.equals("Domain")) {
				category = Whois.SearchFor.DOMAIN;
			} else if (searchForLabel.equals("Organization")) {
				category = Whois.SearchFor.ORGANIZATION;
			} else if (searchForLabel.equals("Group")) {
				category = Whois.SearchFor.GROUP;
			} else if (searchForLabel.equals("Gateway")) {
				category = Whois.SearchFor.GATEWAY;
			} else if (searchForLabel.equals("ASN")) {
				category = Whois.SearchFor.ASN;
			}
			server.setHost(chosenServer.getText());
			return server.lookUpNames(searchString.getText(), category, group, exactMatch.isSelected());
		}

		/*
		 * Executed on the Event Dispatch Thread after
		 * the doInBackground method is finished. The default implementation
		 *  does nothing. Subclasses may override this method
		 *  to perform completion actions on the Event Dispatch Thread.
		 *  Note that you can query status inside the implementation of
		 *  this method to determine the result of this task or whether
		 *  this task has been cancelled.
		 */
		@Override
		protected void done() {
		try {
			names.setText(get());
			} catch (Exception ex) {
			      JOptionPane.showMessageDialog(WhoisGUI.this,
			          ex.getMessage(), "Lookup Failed", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/*
	 * main方法出场了！
	 */
	public static void main(String[] args) {
		try {
			Whois server = new Whois();
			WhoisGUI a = new WhoisGUI(server);
			a.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			a.pack();
			EventQueue.invokeLater(new FrameShower(a));
		} catch (UnknownHostException ex) {
			JOptionPane.showMessageDialog(null, "Could not locate default host " + Whois.DEFAULT_HOST, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private static class FrameShower implements Runnable {
		private final Frame frame;

		FrameShower(Frame frame) {
			this.frame = frame;
		}

		@Override
		public void run() {
			frame.setVisible(true);
		}
	}
}
