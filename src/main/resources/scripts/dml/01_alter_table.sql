ALTER TABLE T_MATCH ADD MATCH_ORDER VARCHAR(20);
ALTER TABLE T_MATCH ADD MATCH_NO_OKOOO VARCHAR(20);
ALTER TABLE T_ODDS ADD O_HOME_BF DOUBLE;
ALTER TABLE T_ODDS ADD O_DRAW_BF DOUBLE;
ALTER TABLE T_ODDS ADD O_AWAY_BF DOUBLE;
ALTER TABLE T_MATCH ADD HALF_TIME_SCORE VARCHAR(10);
ALTER TABLE T_MATCH ADD IS_SYNC CHAR(1);