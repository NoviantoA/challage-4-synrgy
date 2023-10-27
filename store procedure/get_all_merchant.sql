CREATE OR REPLACE FUNCTION public.get_all_merchant()
RETURNS TABLE (
    merchant_id UUID,
    merchant_name VARCHAR,
    merchant_location VARCHAR,
    is_open BOOLEAN
)
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
        merchants AS m;
END;
$function$;

;
