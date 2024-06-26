USE [dbHairSalon]
GO
/****** Object:  StoredProcedure [dbo].[searchStaff]    Script Date: 23.12.2023 15:30:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER   PROCEDURE [dbo].[searchStaff](@Keyword varchar(25))
AS
BEGIN
    SET @Keyword = '%' + @Keyword + '%'; 

    SELECT
        s.[id_staff],
        s.[username],
        s.[first_name],
        s.[last_name],
        s.[tel],
        s.[info],
        s.[active],
        sr.[id_role]  
    FROM Staff s
    LEFT JOIN StaffRole sr ON s.id_staff = sr.id_staff 
    WHERE s.active = 1
    AND (
        s.username LIKE @Keyword OR
        s.first_name LIKE @Keyword OR
        s.last_name LIKE @Keyword OR
        s.tel LIKE @Keyword OR
        s.info LIKE @Keyword
    );
END;