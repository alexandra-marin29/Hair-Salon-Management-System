USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[getAppointmentById]    Script Date: 19.12.2023 23:26:58 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER   PROCEDURE [dbo].[getAppointmentById]
(
 @id integer
)
AS
BEGIN
	SELECT
	   [id_appointment]
	  ,[id_customer]
      ,[id_staff]
      ,[id_service]
      ,[date_app]
      ,[start_hour]
      ,[start_min]
      ,[end_hour]
      ,[end_min]
      ,[info]
      ,[price]
	FROM Appointment WHERE id_appointment=@id;
END;