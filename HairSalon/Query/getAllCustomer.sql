USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[getAllCustomer]    Script Date: 11.12.2023 01:29:04 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER     PROCEDURE [dbo].[getAllCustomer]
AS
BEGIN
	SELECT
	   [id_customer]
	  ,[username]
      ,[first_name]
      ,[last_name]
      ,[tel]
      ,[info]
      ,[active]
	FROM customer WHERE active = 1
	ORDER BY username ASC;
END;