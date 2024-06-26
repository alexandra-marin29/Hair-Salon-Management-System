USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[getAppointmentUserDate]    Script Date: 19.12.2023 23:26:31 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER  PROCEDURE [dbo].[getAppointmentUserDate](
	@Username varchar(25),
	@Date date
)
AS
BEGIN
	SELECT
	   app.[id_appointment]
	  ,app.[id_customer]
      ,app.[id_staff]
      ,app.[id_service]
      ,app.[date_app]
      ,app.[start_hour]
      ,app.[start_min]
      ,app.[end_hour]
      ,app.[end_min]
      ,app.[info]
      ,app.[price]
	FROM Appointment app
	LEFT JOIN staff stf ON stf.id_staff = app.id_staff
	WHERE stf.username = @Username AND
	date_app = @Date;
END;

