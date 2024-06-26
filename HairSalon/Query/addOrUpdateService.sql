USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[addOrUpdateService]    Script Date: 23.12.2023 13:19:13 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER    PROCEDURE [dbo].[addOrUpdateService](
	 @IdService int,
	 @Name varchar(50),
	 @Price money,
	 @Duration int,
	 @Active bit
)
AS
BEGIN
	IF @IdService =-1
		INSERT INTO service(name, price, minutes,active) VALUES
		(@Name, @Price, @Duration, @Active);
	ELSE
		UPDATE service SET
		name = @Name,
		price = @Price,
		minutes = @Duration,
		active = @Active
		WHERE id_service = @IdService;
END