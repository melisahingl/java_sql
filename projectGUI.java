package dbproje;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

public class projectGUI {
	private Connection conn;
	
	private JFrame frame;
	private GridBagConstraints gbc = new GridBagConstraints();
	private JPanel mainPanel;
	private JPanel girisPanel;
	private JPanel userPanel;
	private JPanel kayıtOlPanel;
	private JPanel ilanListelePanel;
	private JPanel profilDüzenlePanel;
	private JPanel basvurulanIlanlarPanel;
	
	private int flag=0;
	
	String eusername;
	String epassword;
	String tür;
	String fiyat;
	int fiyatint;

	
	public projectGUI(Connection conn) {
		this.conn = conn;

		
		initializeGUI();
		initializeMainPanel();
		initializeGirisPanel();
		initializeKayıtOlPanel();
		initializeUserPanel();
		
		showMainPanel();
	}
	
	
	// açılacak guiyi tanımlıyor
	private void initializeGUI() {
        frame = new JFrame("Project GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new CardLayout());
    }

	
	// en başta açılan pencereyi tanımlıyor
	private void initializeMainPanel() {
		mainPanel=new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		JButton girisButton=new JButton("Giriş Yap");
	    gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
		mainPanel.add(girisButton,gbc);
		JButton kayıtOlButton=new JButton("Kayıt Ol");
		gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
		mainPanel.add(kayıtOlButton,gbc);
		
		
		
		// giriş butonuna bastığımız zaman olacakları kontrol ettiğimiz kısım
		girisButton.addActionListener(new ActionListener() {
            
			@Override
            public void actionPerformed(ActionEvent e) {
                showGirisPanel();
            }
        });
		
		
		// kayıt ol butonuna bastığımız zaman olacakları kontrol ettiğimiz kısım
		kayıtOlButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showKayıtOlPanel();
			}
			
		});
	}
	
	// giriş yap penceresini tanımlıyor, giriş yaparken şifre ve kullanıcı adını kontrol ediyor. 
	// doğruysa user panelini gösteriyor. yanlışsa hata veriyor
	private void initializeGirisPanel(){
		girisPanel= new JPanel();
		girisPanel.setLayout(new GridBagLayout());
		
		JLabel label1=new JLabel("Kullanıcı Adı");
		girisPanel.add(label1);
		JTextField usernameField= new JTextField(15);
		girisPanel.add(usernameField);
		
		JLabel label2=new JLabel("Şifre");
		girisPanel.add(label2);
		JPasswordField passwordField= new JPasswordField(15);
		girisPanel.add(passwordField);
		
		JButton girisYap =new JButton("Giriş Yap");
		girisPanel.add(girisYap);
		
		
		//giriş yapmak için butona bastığımız zaman olacakları kontrol ediyor
		girisYap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				eusername = usernameField.getText();
				epassword = new String(passwordField.getPassword());
				String query="SELECT * FROM tablo";
				Statement s;
				flag=0;
				try {
					s = conn.createStatement();
					ResultSet r = s.executeQuery(query);
					
					while(r.next()) {
						
						if(eusername.equals(r.getString("username")) && epassword.equals(r.getString("pword"))){
							flag=1;
							
							// bilgiler doğruysa giriş yap
							showUserPanel();
						}
						}
					if (flag==0) {
		                JOptionPane.showMessageDialog(frame, "Hatalı kullanıcı ismi ya da şifre.", "Başarısız", JOptionPane.ERROR_MESSAGE);
		            }
					
				}
				catch (SQLException e1) {
								e1.printStackTrace();
				}	
			}
		});
		
	}
	
	// kayıt olma penceresini tanımlıyor. kayıt olurken yapılacak kontrolleri yapıyor. kullanıcı adı aynı mı ona bakıyor,
	// yaş integer mi ve 18 ile 100 arasında mı ona bakıyor. eğer her şey yolundaysa veritabanına yeni bir kullanıcı ekliyor.
	private void initializeKayıtOlPanel() {
		kayıtOlPanel = new JPanel();
	    kayıtOlPanel.setLayout(new GridBagLayout());
	    flag = 0;
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.WEST;
	    gbc.insets = new Insets(5, 5, 5, 5); // Adjust insets as needed

	    JLabel label1 = new JLabel("Kullanıcı Adı");
	    kayıtOlPanel.add(label1, gbc);

	    gbc.gridx = 1;
	    JTextField usernameField = new JTextField(15);
	    kayıtOlPanel.add(usernameField, gbc);

	    gbc.gridx = 0;
	    gbc.gridy++;
	    JLabel label2 = new JLabel("Şifre");
	    kayıtOlPanel.add(label2, gbc);

	    gbc.gridx = 1;
	    JTextField passwordField = new JTextField(15);
	    kayıtOlPanel.add(passwordField, gbc);

	    gbc.gridx = 0;
	    gbc.gridy++;
	    JLabel label3 = new JLabel("Şehir");
	    kayıtOlPanel.add(label3, gbc);

	    gbc.gridx = 1;
	    JTextField cityField = new JTextField(15);
	    kayıtOlPanel.add(cityField, gbc);

	    gbc.gridx = 0;
	    gbc.gridy++;
	    JLabel label4 = new JLabel("Yaş");
	    kayıtOlPanel.add(label4, gbc);

	    gbc.gridx = 1;
	    JTextField ageField = new JTextField(15);
	    kayıtOlPanel.add(ageField, gbc);

	    gbc.gridx = 0;
	    gbc.gridy++;
	    gbc.gridwidth = 2; // Span two columns for the button
	    JButton kayıtOl = new JButton("Kayıt Ol");
	    kayıtOlPanel.add(kayıtOl, gbc);
		
		kayıtOl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 String username = usernameField.getText();
		         String password = passwordField.getText();
		         String city = cityField.getText();
		         String ageStr = ageField.getText();
		         flag = 0;
		         
		         int age = 0;
		         
		         try {

		             age = Integer.parseInt(ageStr);

		             if (age < 18 || age > 100) {
		                 JOptionPane.showMessageDialog(frame, "Yaş değeri 18 ile 100 arasında olmalıdır.",
		                         "Başarısız", JOptionPane.ERROR_MESSAGE);
		                 return; 
		             }

		         } catch (NumberFormatException ex) {
		             JOptionPane.showMessageDialog(frame, "Yaş değeri geçerli bir tamsayı değil.",
		                     "Başarısız", JOptionPane.ERROR_MESSAGE);
		             return; 
		         }

		         String query = "SELECT username FROM kullanici";
		         Statement s;
		         try {
		             s = conn.createStatement();
		             ResultSet r = s.executeQuery(query);

		             while (r.next()) {
		                 if (username.equals(r.getString("username"))) {
		                     JOptionPane.showMessageDialog(frame, "Kullanıcı adı kullanılmaktadır, başka kullanıcı adı giriniz.",
		                             "Başarısız", JOptionPane.ERROR_MESSAGE);
		                     flag = 1;
		                     return;  
		                 }
		             }

		         } catch (SQLException e1) {
		             e1.printStackTrace();
		             return;  
		         }

		         if (flag == 0) {
		        	 JOptionPane.showMessageDialog(frame, "Kayıt olma başarıyla tamamlandı.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
		             String queryInsert = "SELECT InsertUser(?,?,?,?);";
		             try {
		                 PreparedStatement psInsert = conn.prepareStatement(queryInsert);
		                 psInsert.setString(1, username);
		                 psInsert.setString(2, password);
		                 psInsert.setString(3, city);
		                 psInsert.setInt(4, age);
		                 psInsert.executeQuery();
		                 showMainPanel();

		             } catch (SQLException e1) {
		                 e1.printStackTrace();
		             }
		         }
		     }
		});
	}
	
	// kullanıcı profilini gösteriyor
	private void initializeUserPanel() {
		frame.getContentPane().removeAll();
		userPanel= new JPanel();
		userPanel.setLayout(new GridBagLayout());
		
		JLabel label1= new JLabel("İlanın görütülemek istediğiniz hayvan türünü seçiniz.");
		gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
		userPanel.add(label1,gbc);
		
		JComboBox<String> hayvanTuruComboBox = new JComboBox<>();
	    gbc.gridx = 2;
	    gbc.gridy = 0;
	    gbc.insets = new Insets(10, 10, 10, 10);
	    userPanel.add(hayvanTuruComboBox, gbc);

	    // Populate the JComboBox with unique values of "hayvan_türü" from the database
	    populateHayvanTuruComboBox(hayvanTuruComboBox);
		
		
		/* JTextField türField= new JTextField(15);
		gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
		userPanel.add(türField,gbc); */
		
		JLabel label2= new JLabel("Fiyat sınırı giriniz.");
		gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
		userPanel.add(label2,gbc);
		
		JTextField fiyatField= new JTextField(15);
		gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
		userPanel.add(fiyatField,gbc);
		
		JButton ilanListeleButton= new JButton("İlanları Listele");
		gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 10, 10);
		userPanel.add(ilanListeleButton,gbc);
		
		JButton profilDüzenleButton= new JButton("Profili Düzenle");
		gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        userPanel.add(profilDüzenleButton,gbc);
        
        JButton basvurulanIlanlarButton=new JButton("Başvurduğum ilanları görüntüle");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        userPanel.add(basvurulanIlanlarButton,gbc);
        
        JButton cikisYap=new JButton("Çıkış");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        userPanel.add(cikisYap,gbc);
        
        ilanListeleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				tür = (String) hayvanTuruComboBox.getSelectedItem();
				fiyat = fiyatField.getText();
				fiyatint = Integer.parseInt(fiyat);
				initializeIlanListelePanel(tür, fiyatint);
				showIlanListelePanel();
			}
        	
        });
        
        profilDüzenleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initializeProfilDüzenlePanel();
				showProfilDüzenlePanel();
			}	
        });
        
        basvurulanIlanlarButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initializeBasvurulanIlanlarPanel();
				showBasvurulanIlanlarPanel();
			}
        	
        });
        
        cikisYap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showMainPanel();		
			}
        	
        });
	}
	
	//ilan listeleme penceresini gösteriyor
	private void initializeIlanListelePanel(String tür, int fiyat) {
		
		List<Integer> list = new ArrayList<>();
		GridBagConstraints gbc = new GridBagConstraints();
		ilanListelePanel=new JPanel();
		ilanListelePanel.setLayout(new GridBagLayout());
			

		    String query = "(SELECT k.username as ilan_sahibi, h.animal_type as hayvan_turu," +
		               "a.animal_id as hayvan_id, a.fiyat as hayvan_fiyat, ap.basvuran_isim as basvuran_isim," +
		               "ap.basvuran_sehir as basvuran_sehir " +
		               "FROM kullanici k " +
		               "JOIN adoption a ON k.username = a.username " +
		               "JOIN animal h ON h.animal_id = a.animal_id " +
		               "LEFT JOIN (SELECT a.adoption_id, k.username as basvuran_isim," +
		               "k.city as basvuran_sehir " +
		               "FROM application a " +
		               "JOIN kullanici k ON a.username = k.username) ap ON a.adoption_id = ap.adoption_id " +
		               "WHERE h.animal_type = ?) " +
		               "INTERSECT " +
		               "(SELECT k.username as ilan_sahibi, h.animal_type as hayvan_turu," +
		               "a.animal_id as hayvan_id, a.fiyat as hayvan_fiyat, ap.basvuran_isim as basvuran_isim," +
		               "ap.basvuran_sehir as basvuran_sehir " +
		               "FROM kullanici k " +
		               "JOIN adoption a ON k.username = a.username " +
		               "JOIN animal h ON h.animal_id = a.animal_id " +
		               "LEFT JOIN (SELECT a.adoption_id, k.username as basvuran_isim," +
		               "k.city as basvuran_sehir " +
		               "FROM application a " +
		               "JOIN kullanici k ON a.username = k.username) ap ON a.adoption_id = ap.adoption_id " +
		               "WHERE a.fiyat <= ?)"+
		               "ORDER BY hayvan_id ASC";
		    try (PreparedStatement p = conn.prepareStatement(query)) {
		    	
		        p.setString(1, tür);
		        p.setInt(2, fiyat);
		        
		        try (ResultSet r = p.executeQuery()) {
		            // Create a table model and populate it with data
		            Object[] column = {"İlan Sahibi", "Hayvan Türü", "Hayvan ID", "Fiyat", "Başvuran İsim", "Başvuran Şehir"};
		            DefaultTableModel tableModel = new DefaultTableModel(column, 0);
		            
		            while (r.next()) {
		                String ilanSahibi = r.getString("ilan_sahibi");
		                String hayvanTuru = r.getString("hayvan_turu");
		                String hayvanId = r.getString("hayvan_id");
		                int hayvanFiyat = r.getInt("hayvan_fiyat");
		                String basvuranIsim = r.getString("basvuran_isim");
		                String basvuranSehir = r.getString("basvuran_sehir");

		                Object[] row = {ilanSahibi, hayvanTuru, hayvanId, hayvanFiyat, basvuranIsim, basvuranSehir};
		                tableModel.addRow(row);
		                list.add(Integer.parseInt(hayvanId));
		            }

		         // Create a JTable with the populated model
		            JTable tbl = new JTable(tableModel);
		            JScrollPane js = new JScrollPane(tbl);

		            gbc.gridx = 0;
		            gbc.gridy = 1;
		            gbc.gridwidth = 3;
		            gbc.insets = new Insets(10, 10, 10, 10);
		            ilanListelePanel.add(js, gbc);
		  
		        }
		    } catch (SQLException e1) {
		        e1.printStackTrace();
		    }
		    
		 String query2="SELECT count(*)\r\n"
		 		+ "FROM (SELECT count(*)\r\n"
		 		+ "FROM (\r\n"
		 		+ "    SELECT k.username as ilan_sahibi, h.animal_type as hayvan_turu, a.animal_id as hayvan_id,"
		 		+ "    a.fiyat as hayvan_fiyat, ap.basvuran_isim as basvuran_isim, ap.basvuran_sehir as basvuran_sehir\r\n"
		 		+ "    FROM kullanici k\r\n"
		 		+ "    JOIN adoption a ON k.username = a.username\r\n"
		 		+ "    JOIN animal h ON h.animal_id = a.animal_id\r\n"
		 		+ "    LEFT JOIN (\r\n"
		 		+ "        SELECT a.adoption_id, k.username as basvuran_isim, k.city as basvuran_sehir\r\n"
		 		+ "        FROM application a\r\n"
		 		+ "        JOIN kullanici k ON a.username = k.username\r\n"
		 		+ "    ) ap ON a.adoption_id = ap.adoption_id\r\n"
		 		+ "    WHERE h.animal_type = ?\r\n"
		 		+ "    INTERSECT\r\n"
		 		+ "    SELECT k.username as ilan_sahibi, h.animal_type as hayvan_turu, a.animal_id as hayvan_id, a.fiyat as hayvan_fiyat,"
		 		+ "     ap.basvuran_isim as basvuran_isim, ap.basvuran_sehir as basvuran_sehir\r\n"
		 		+ "    FROM kullanici k\r\n"
		 		+ "    JOIN adoption a ON k.username = a.username\r\n"
		 		+ "    JOIN animal h ON h.animal_id = a.animal_id\r\n"
		 		+ "    LEFT JOIN (\r\n"
		 		+ "        SELECT a.adoption_id, k.username as basvuran_isim, k.city as basvuran_sehir\r\n"
		 		+ "        FROM application a\r\n"
		 		+ "        JOIN kullanici k ON a.username = k.username\r\n"
		 		+ "    ) ap ON a.adoption_id = ap.adoption_id\r\n"
		 		+ "    WHERE a.fiyat <= ?\r\n"
		 		+ "    ORDER BY hayvan_id ASC\r\n"
		 		+ ") AS subquery\r\n"
		 		+ "GROUP BY hayvan_id,hayvan_turu\r\n"
		 		+ "HAVING hayvan_turu=?);\r\n"
		 		+ "";
		 
		 
		 
		 try (PreparedStatement p = conn.prepareStatement(query2)) {
			    p.setString(1, tür);
		        p.setInt(2, fiyat);
		        p.setString(3, tür);
		        String str=null;
		        try (ResultSet r = p.executeQuery()) {
		            while (r.next()) {
		            	str=r.getString("count");
		            }
		            String str2=str+" farklı ilan mevcut";
	                JLabel label=new JLabel(str2);
	                gbc.gridx = 0;
		            gbc.gridy = 10;
		            gbc.insets = new Insets(10, 10, 10, 10);
		            ilanListelePanel.add(label, gbc);
		        }
		    } catch (SQLException e1) {
		        e1.printStackTrace();
		    }
		 
		 
		 
		 
		 
		 
		    
		    
		    
		JLabel label1= new JLabel("Başvurulacak hayvan ID giriniz:");
	    gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.insets = new Insets(10, 10, 10, 10);
		ilanListelePanel.add(label1,gbc);
		
		JTextField hayvanID=new JTextField(15);
	    gbc.gridx = 2;
        gbc.gridy = 12;
        gbc.insets = new Insets(10, 10, 10, 10);
		ilanListelePanel.add(hayvanID,gbc);
		
		JButton basvurButton = new JButton("Başvur");
	    gbc.gridx = 2;
	    gbc.gridy = 13; // Adjusted the y-coordinate
	    gbc.insets = new Insets(10, 10, 10, 10);
	    ilanListelePanel.add(basvurButton, gbc);

	    JButton geriButton = new JButton("Geri");
	    gbc.gridx = 1;
	    gbc.gridy = 14; // Adjusted the y-coordinate
	    gbc.insets = new Insets(10, 10, 10, 10);
	    ilanListelePanel.add(geriButton, gbc);
		
		basvurButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(list.contains(Integer.parseInt(hayvanID.getText()))) {
					
					String query="INSERT INTO application (adoption_id, username) VALUES (fonk(?), ?);";
					 try {
		                 PreparedStatement psInsert = conn.prepareStatement(query);
		                 psInsert.setInt(1,Integer.parseInt(hayvanID.getText()));
		                 psInsert.setString(2,eusername);
		                 psInsert.executeUpdate();
		                 JOptionPane.showMessageDialog(frame, "Başvuru başarıyla gerçekleşti.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
		                 initializeIlanListelePanel(tür, fiyatint);
		                 showIlanListelePanel();

		             } catch (SQLException e1) {
		                 e1.printStackTrace();
		             }				
				}else JOptionPane.showMessageDialog(frame, "Hatalı HayvanID Girişi.", "Başarısız", JOptionPane.ERROR_MESSAGE);
			}
		});
		geriButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showUserPanel();
			}
			
		});	
	}
	
	private void initializeProfilDüzenlePanel() {
		profilDüzenlePanel=new JPanel();
		profilDüzenlePanel.setLayout(new GridBagLayout());
		
		JLabel label1=new JLabel("Şifre Değiştir");
		gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
		profilDüzenlePanel.add(label1,gbc);
		
		JTextField sifreField= new JTextField(15);
		gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
		profilDüzenlePanel.add(sifreField,gbc);
		
		JButton guncelleButton1 = new JButton("Güncelle");
		gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        profilDüzenlePanel.add(guncelleButton1,gbc);
        
		JLabel label2=new JLabel("Şehir Değiştir");
		gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
		profilDüzenlePanel.add(label2,gbc);
		
		JTextField sehirField= new JTextField(15);
		gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
		profilDüzenlePanel.add(sehirField,gbc);
		
		JButton guncelleButton2 = new JButton("Güncelle");
		gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        profilDüzenlePanel.add(guncelleButton2,gbc);
        
        JButton geri=new JButton("Geri");
		gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        profilDüzenlePanel.add(geri,gbc);
        
        guncelleButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//ŞİFRE GÜNCELLEMESİYLE İLGİLİ YAPILACAKLAR YAZILIR.
				String query="UPDATE kullanici SET pword=(?) WHERE username=(?);";
				try {
					JOptionPane.showMessageDialog(frame, "Şifre Başarıyla Değiştirildi. Trigger çalıştı", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
	                 PreparedStatement psInsert = conn.prepareStatement(query);
	                 psInsert.setString(1, sifreField.getText());
	                 psInsert.setString(2,eusername);
	                 psInsert.executeUpdate();
	                 showProfilDüzenlePanel();

	             } catch (SQLException e1) {
	                 e1.printStackTrace();
	             }
				
			}
        });
        
        guncelleButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//ŞEHİR GÜNCELLEMESİYLE İLGİLİ YAPILACAKLAR YAZILIR.
				String query="UPDATE kullanici SET city=(?) WHERE username=(?);";
				try {
					JOptionPane.showMessageDialog(frame, "Şehir Başarıyla Değiştirildi. Trigger çalıştı", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
	                 PreparedStatement psInsert = conn.prepareStatement(query);
	                 psInsert.setString(1, sehirField.getText());
	                 psInsert.setString(2,eusername);
	                 psInsert.executeUpdate();
	                 showProfilDüzenlePanel();

	             } catch (SQLException e1) {
	                 e1.printStackTrace();
	             }
				
			}
        });
        
        geri.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showUserPanel();
			}
        });
	}
	
	private void initializeBasvurulanIlanlarPanel() {
		GridBagConstraints gbc = new GridBagConstraints();
		basvurulanIlanlarPanel=new JPanel();
		basvurulanIlanlarPanel.setLayout(new GridBagLayout());
	    //BAŞVURULARIMI GÖSTER BUTONUNA BASILDIĞINDA YAPILACAKLAR YAZILIR.
		List<Integer> list = new ArrayList<>();	
		String query ="SELECT adoption.username as ilan_sahibi,\r\n"
				+ "animal.animal_id as hayvan_ID,\r\n"
				+ "animal.animal_type as hayvan_türü\r\n"
				+ "FROM application,adoption,animal\r\n"
				+ "WHERE application.username=(?) and \r\n"
				+ "application.adoption_id=adoption.adoption_id and \r\n"
				+ "animal.animal_id=adoption.animal_id;";
         try (PreparedStatement p = conn.prepareStatement(query)) {
 	
              p.setString(1, eusername);
      try (ResultSet r = p.executeQuery()) {
         // Create a table model and populate it with data
         Object[] column = {"İlan Sahibi", "Hayvan Id", "Hayvan Türü"};
         DefaultTableModel tableModel = new DefaultTableModel(column, 0);
         
         while (r.next()) {
             String ilanSahibi = r.getString("ilan_sahibi");
             String hayvanId = r.getString("hayvan_id");
             String hayvanTuru = r.getString("hayvan_türü");
             Object[] row = {ilanSahibi, hayvanId, hayvanTuru};
             tableModel.addRow(row);
             list.add(Integer.parseInt(hayvanId));
         }
      // Create a JTable with the populated model
         JTable tbl = new JTable(tableModel);
         JScrollPane js = new JScrollPane(tbl);

         gbc.gridx = 0;
         gbc.gridy = 1;
         gbc.gridwidth = 3;
         gbc.insets = new Insets(10, 10, 10, 10);
         basvurulanIlanlarPanel.add(js, gbc);

     }
 } catch (SQLException e1) {
     e1.printStackTrace();
 }
		
		JLabel label1=new JLabel("Başvurusu silinecek hayvan ID girin");
		gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.insets = new Insets(10, 10, 10, 10);
        basvurulanIlanlarPanel.add(label1,gbc);
		
		JTextField hayvanID= new JTextField(15);
		gbc.gridx = 2;
        gbc.gridy = 11;
        gbc.insets = new Insets(10, 10, 10, 10);
        basvurulanIlanlarPanel.add(hayvanID,gbc);
		
		JButton silButton = new JButton("Sil");
		gbc.gridx = 2;
        gbc.gridy = 12;
        gbc.insets = new Insets(10, 10, 10, 10);
        basvurulanIlanlarPanel.add(silButton,gbc);
        
        JButton geri = new JButton("Geri");
		gbc.gridx = 1;
        gbc.gridy = 13;
        gbc.insets = new Insets(10, 10, 10, 10);
        basvurulanIlanlarPanel.add(geri,gbc);
        
        silButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// SİL BUTONUNA BASILINCA YAPILACAKLAR BURDA YAZILIR.
                    if(list.contains(Integer.parseInt(hayvanID.getText()))) {
					String query="SELECT basvuru_sil(?,?)";
					 try {
		                 PreparedStatement psInsert = conn.prepareStatement(query);
		                 psInsert.setString(1,eusername);
		                 psInsert.setInt(2,Integer.parseInt(hayvanID.getText())); 
		                 psInsert.executeQuery();
		                 JOptionPane.showMessageDialog(frame, "Başvuru silme başarıyla gerçekleşti.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
		                 initializeBasvurulanIlanlarPanel();
		                 showBasvurulanIlanlarPanel();

		             } catch (SQLException e1) {
		                 e1.printStackTrace();
		             }
				}else JOptionPane.showMessageDialog(frame, "Hatalı HayvanID Girişi.", "Başarısız", JOptionPane.ERROR_MESSAGE);
			}
        });
        
        geri.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showUserPanel();		
			}
        });
	}
	
	public void display() {
        frame.setVisible(true);
    }
	
	
	private void populateHayvanTuruComboBox(JComboBox<String> comboBox) {
	    String query = "SELECT DISTINCT animal_type FROM animal";
	    try (Statement statement = conn.createStatement();
	         ResultSet resultSet = statement.executeQuery(query)) {
	        while (resultSet.next()) {
	            String hayvanTuru = resultSet.getString("animal_type");
	            comboBox.addItem(hayvanTuru);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	private void showBasvurulanIlanlarPanel() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(basvurulanIlanlarPanel);
        frame.revalidate();
        frame.repaint();
		
	}
	
	private void showProfilDüzenlePanel() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(profilDüzenlePanel);
        frame.revalidate();
        frame.repaint();
	}
	
	private void showIlanListelePanel() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(ilanListelePanel);
        frame.revalidate();
        frame.repaint();
		
	}
	
    private void showMainPanel() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(mainPanel);
        frame.revalidate();
        frame.repaint();
    }
    
    private void showGirisPanel() {
    	frame.getContentPane().removeAll();
        frame.getContentPane().add(girisPanel);
        frame.revalidate();
        frame.repaint();
    }
    
    private void showKayıtOlPanel() {
    	frame.getContentPane().removeAll();
        frame.getContentPane().add(kayıtOlPanel);
        frame.revalidate();
        frame.repaint();
    }
    
    private void showUserPanel() {
    	frame.getContentPane().removeAll();
        frame.getContentPane().add(userPanel);
        frame.revalidate();
        frame.repaint();
    	
    }

}
