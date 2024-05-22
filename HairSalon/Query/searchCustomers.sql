USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[searchCustomers]    Script Date: 11.12.2023 01:16:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[searchCustomers](@Keyword varchar(25))
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
	(
		username like @Keyword OR
		first_name like @Keyword OR
		last_name like @Keyword OR
		tel like @Keyword OR
		info like @Keyword
	);
END;