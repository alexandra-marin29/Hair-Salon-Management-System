USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[addOrUpdateCustomer]    Script Date: 11.12.2023 01:34:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER    PROCEDURE [dbo].[addOrUpdateCustomer](
	 @IdCustomer int,
	 @Username varchar(25),
	 @FName varchar(25),
	 @LName varchar(25),
	 @Tel varchar(25),
	 @Info varchar(25)
)
AS
BEGIN
	IF @IdCustomer =-1
		INSERT INTO customer(username, first_name, last_name, tel, info) VALUES
		(@Username, @FName, @LName, @Tel, @Info);
	ELSE
		UPDATE customer SET
		username = @Username,
		first_name = @FName,
		last_name = @LName,
		tel = @Tel,
		info = @Info
		WHERE id_customer = @IdCustomer;
END