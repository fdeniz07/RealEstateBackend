-- IMPL-010-1: global case-insensitive slug uniqueness (M1 policy)
-- Table/column verified in V1 baseline: public.adverts.slug

CREATE UNIQUE INDEX ux_adverts_slug_lower ON public.adverts (lower(slug));