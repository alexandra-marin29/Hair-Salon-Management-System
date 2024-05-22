USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[deleteCustomer]    Script Date: 11.12.2023 01:31:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[deleteCustomer]
(
 @id integer
)
AS
BEGIN
	UPDATE customer
	SET active = 0
	WHERE id_customer = @id;
END;