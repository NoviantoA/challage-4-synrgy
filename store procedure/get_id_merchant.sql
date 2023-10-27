CREATE OR REPLACE FUNCTION public.get_id_merchant(p_merchant_id uuid)
 RETURNS TABLE(merchant_id uuid, merchant_name character varying, merchant_location character varying, is_open boolean)
 LANGUAGE plpgsql
AS $function$
BEGIN
    RETURN QUERY
    SELECT
        m.id AS merchant_id,
        m.merchant_name,
        m.merchant_location,
        m.open AS is_open
    FROM
        merchants AS m
    WHERE
        m.id = p_merchant_id;
END;
$function$
;
