
BEGIN
-- PROC_DELETE_DUPLICATE_DATE

-- /* *********************************************************************************/
-- /* 第一个cursor，获得所有存在重复值（即分组后count>1）的MatchNo                 ****/
-- /* 第二个cursor，内嵌，根据matchNo 获取数据（升序）后，逐个比较，				****/
-- /* 若相同，则删除，并再向下比较；若不同，则取新数据，继续往下比较				****/
/* *********************************************************************************/

	-- 声明变量
	DECLARE V_TOTAL_COUNT INT;
	DECLARE P_MATCH_NO VARCHAR(20);
	DECLARE S1,S2 INT DEFAULT 0;

	/* ***** CURSOR_1 *******/
	DECLARE CURSOR_1 CURSOR 
	FOR 
	SELECT MATCH_NO FROM T_ODDS GROUP BY MATCH_NO HAVING COUNT(1)>1;

	SELECT COUNT(1) INTO V_TOTAL_COUNT FROM (SELECT MATCH_NO FROM T_ODDS GROUP BY MATCH_NO HAVING COUNT(1)>1 ) T;

	/* ***** CURSOR_1 BEGIN *******/
	OPEN CURSOR_1;
	WHILE S1 < V_TOTAL_COUNT+1 DO
		-- 取值
		FETCH CURSOR_1 INTO P_MATCH_NO;

		BEGIN
			-- 声明 cursor 2 变量
			DECLARE IS_CUR_END  INT(1) DEFAULT 0;
			DECLARE P_ODDS_ID INT DEFAULT 0;
			DECLARE P_HOME_ODDS,P_DRAW_ODDS,P_AWAY_ODDS,P_HOME_ODDS_2,P_DRAW_ODDS_2,P_AWAY_ODDS_2 DOUBLE;
			DECLARE V_HOME_ODDS,V_DRAW_ODDS,V_AWAY_ODDS,V_HOME_ODDS_2,V_DRAW_ODDS_2,V_AWAY_ODDS_2 DOUBLE DEFAULT 0;
			
			/* ***** CURSOR_2 *******/
			DECLARE CURSOR_2 CURSOR 
			FOR 
			SELECT ODDS_ID, O_HOME_2,O_DRAW_2,O_AWAY_2 FROM T_ODDS WHERE MATCH_NO = P_MATCH_NO;

			DECLARE CONTINUE HANDLER FOR NOT FOUND SET IS_CUR_END = 1;  

			-- 每次遍历，做记号
			SET S2 =0;
			
			/* ***** cursor_2 begin *******/
			OPEN CURSOR_2;
			-- 取值
			FETCH CURSOR_2 INTO P_ODDS_ID,P_HOME_ODDS_2,P_DRAW_ODDS_2,P_AWAY_ODDS_2;

			WHILE (IS_CUR_END != 1) DO
				IF S2 = 0 THEN
					SET V_HOME_ODDS_2=P_HOME_ODDS_2;
					SET V_DRAW_ODDS_2=P_DRAW_ODDS_2;
					SET V_AWAY_ODDS_2=P_AWAY_ODDS_2;
				ELSE 
					IF V_HOME_ODDS_2=P_HOME_ODDS_2 AND V_DRAW_ODDS_2=P_DRAW_ODDS_2 AND V_AWAY_ODDS_2=P_AWAY_ODDS_2
					THEN
						-- DELETE 
						DELETE FROM T_ODDS WHERE ODDS_ID = P_ODDS_ID;
					ELSE
						SET V_HOME_ODDS_2=P_HOME_ODDS_2;
						SET V_DRAW_ODDS_2=P_DRAW_ODDS_2;
						SET V_AWAY_ODDS_2=P_AWAY_ODDS_2;
					END IF;				
				END IF;

				SET S2=S2+1;
			END WHILE;
			CLOSE CURSOR_2;
		END;
		/* ***** cursor_2 end *******/
		
		SET S1=S1+1;
	END WHILE;
	CLOSE CURSOR_1;
	/* ***** cursor_1 end *******/

	-- 提交事物
	COMMIT;
 
END;