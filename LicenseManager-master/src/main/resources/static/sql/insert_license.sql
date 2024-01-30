set client_encoding to 'UTF8';
--20231121 funayama
--INSERT INTO license(id,name,level,higher_license_id) VALUES (1,'Cisco CCIE',3,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (1,'LPIC レベル3',3,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (2,'Oracle認定エンタープライズアーキテクトEE 5（旧SJC-EA）',3,NULL);
--20231121 funayama
--INSERT INTO license(id,name,level,higher_license_id) VALUES (4,'Oracle認定 ORACLE MASTER Plutinum Oracle(Database)',3,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (3,'情報処理技術者試験プロジェクトマネージャ試験',3,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (4,'情報処理技術者試験ネットワークスペシャリスト試験',3,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (5,'情報処理技術者試験データベーススペシャリスト試験',3,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (6,'情報処理技術者試験情報処理安全確保支援士試験（旧情報セキュリティスペシャリスト試験）',3,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (7,'情報処理技術者試験ITサービスマネージャ試験',3,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (8,'ソリューションアーキテクト　プロフェッショナル',3,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (9,'DevOpsエンジニア - プロフェッショナル',3,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (10,'Azure DevOps Engineer Expert',3,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (11,'Azure Solutions Architect Expert',3,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (12,'ソリューションアーキテクト　アソシエイト',2,'{8}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (13,'SysOpsアドミニストレーター - アソシエイト',2,'{9}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (14,'デベロッパー - アソシエイト',2,'{9}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (15,'Alexaスキルビルダー - 専門知識',2,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (16,'機械学習 - 専門知識',2,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (17,'セキュリティ - 専門知識',2,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (18,'ビッグデータ - 専門知識',2,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (19,'高度なネットワーキング - 専門知識',2,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (20,'Azure 管理者アソシエイツ',2,'{10}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (21,'Azure Developer アソシエイツ',2,'{10}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (22,'Azure Security Engineer アソシエイツ',2,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (23,'一般社団法人　日本ディープラーニング協会認定　E資格',2,NULL);
--20231121 funayama
--INSERT INTO license(id,name,level,higher_license_id) VALUES (26,'Cisco CCNP',2,'{1}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (24,'Cisco CCNP',2,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (25,'LPIC レベル2',2,'{1}');
--20231121 funayama
--INSERT INTO license(id,name,level,higher_license_id) VALUES (28,'Oracle認定 ORACLE MASTER Gold Oracle（Database)',2,'{4}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (26,'Oracle認定 ORACLE MASTER Gold Oracle（Database)',2,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (27,'Ruby技術者認定試験 Rubyアソシエイト認定試験 Ruby Programmer GOLD',2,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (28,'LPI Japan認定OSS-DB Gold',2,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (29,'Oracle認定　JavaプログラマGold(旧 OJCP/OJCーWC/OJCーWS/OJCーMA/OJCーD)',2,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (30,'WebコンポーネントディベロッパEE 5(旧SJCーWC)',2,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (31,'ビジネスコンポーネントディベロッパEE 1.3(旧SJCーBC)',2,'{30}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (32,'Java WebサービスディベロッパEE 1.4(旧SJCーWS)',2,'{30,31}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (33,'モバイルアプリケーションディベロッパ(旧SJCーMA)',2,'{30,31,32}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (34,'Javaディベロッパ(旧SJCーD)',2,'{30,31,32,33}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (35,'AWS認定　クラウドプラクティショナー',1,'{8,9,12,13,14}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (36,'Microsoft認定 Azure　Fundamentals',1,'{10,11,20,21,22}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (37,'一般社団法人　日本ディープラーニング協会認定　G検定',1,'{23}');
--20231121 funayama
--INSERT INTO license(id,name,level,higher_license_id) VALUES (40,'Cisco CCNA',1,'{1,26}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (38,'Cisco CCNA',1,'{24}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (39,'LPIC　レベル1',1,'{1,25}');
--20231121 funayama
--INSERT INTO license(id,name,level,higher_license_id) VALUES (42,'Oracle認定 ORACLE MASTERＳｉｌｖｅｒ　Oracle(Database)',1,'{4,28}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (40,'Oracle認定 ORACLE MASTERＳｉｌｖｅｒ　Oracle(Database)',1,'{26}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (41,'Oracle認定　JavaプログラマSilver（旧認定 SJCーP/OJCーP）',1,'{29}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (42,'情報処理技術者試験　応用情報技術者試験（27年4月追加）',2,'{3,4,5,6,7}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (43,'IoT検定レベル１プロフェッショナル・コーディネータ（28年10月追加）',1,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (44,'PHP技術者認定上級試験（対象　上級認定）（29年5月追加）',1,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (45,'Ruby技術者認定試験 Rubyアソシエイト認定試験 Ruby Programmer Silver',1,'{27}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (46,'LPIJapan認定OSS-DB Silver',1,'{28}');
INSERT INTO license(id,name,level,higher_license_id) VALUES (47,'Python 3 エンジニア認定データ分析試験',1,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (48,'Pythonエンジニア認定試験（29年11月追加）',1,NULL);
INSERT INTO license(id,name,level,higher_license_id) VALUES (49,'情報処理技術者試験　基本情報技術者試験',1,'{3,4,5,6,7,42}');
