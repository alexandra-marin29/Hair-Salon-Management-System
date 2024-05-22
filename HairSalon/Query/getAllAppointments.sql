USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[getAllAppointments]    Script Date: 23.12.2023 11:21:50 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER     PROCEDURE [dbo].[getAllAppointments]
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
	FROM Appointment WHERE active = 1
	
END;