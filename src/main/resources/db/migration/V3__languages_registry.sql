-- IMPL-LREG-01: Language Registry (ARCH-2 v0.1.1, DEC-007)
-- Additive only — does not modify adverts or V2 slug index.

CREATE TABLE public.languages (
    code character varying(35) NOT NULL,
    parent_code character varying(35),
    name character varying(100) NOT NULL,
    native_name character varying(100) NOT NULL,
    is_active boolean NOT NULL,
    is_ui_supported boolean NOT NULL,
    is_content_supported boolean NOT NULL,
    text_direction character(3) NOT NULL,
    sort_order smallint NOT NULL,
    created_at timestamp(6) without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp(6) without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT languages_pkey PRIMARY KEY (code),
    CONSTRAINT languages_parent_code_fkey FOREIGN KEY (parent_code) REFERENCES public.languages (code) ON DELETE RESTRICT,
    CONSTRAINT languages_text_direction_check CHECK (text_direction IN ('LTR', 'RTL'))
);

INSERT INTO public.languages (
    code,
    parent_code,
    name,
    native_name,
    is_active,
    is_ui_supported,
    is_content_supported,
    text_direction,
    sort_order
) VALUES
    ('de', NULL, 'German', 'Deutsch', true, true, true, 'LTR', 10),
    ('tr', NULL, 'Turkish', 'Türkçe', true, true, true, 'LTR', 20),
    ('en', NULL, 'English', 'English', true, true, true, 'LTR', 30);