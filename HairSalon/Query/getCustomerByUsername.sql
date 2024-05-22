USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[getCustomerByUsername]    Script Date: 11.12.2023 01:24:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[getCustomerByUsername](@Username varchar(25))
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
    FROM customer WHERE active = 1 AND
	username = @Username;
END;