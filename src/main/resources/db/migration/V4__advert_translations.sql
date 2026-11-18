-- ARCH-3B / DEC-007: AdvertTranslation persistence foundation (schema only, no backfill)

ALTER TABLE public.adverts
    ADD COLUMN source_language_code character varying(35);

ALTER TABLE public.adverts
    ADD CONSTRAINT adverts_source_language_code_fkey
        FOREIGN KEY (source_language_code) REFERENCES public.languages (code) ON DELETE RESTRICT;

CREATE TABLE public.advert_translations (
    id bigint NOT NULL,
    advert_id bigint NOT NULL,
    language_code character varying(35) NOT NULL,
    title character varying(150) NOT NULL,
    "desc" character varying(300),
    slug character varying(200) NOT NULL,
    translation_publication_status character varying(32) NOT NULL,
    create_at timestamp(6) without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at timestamp(6) without time zone,
    CONSTRAINT advert_translations_pkey PRIMARY KEY (id),
    CONSTRAINT advert_translations_advert_id_fkey
        FOREIGN KEY (advert_id) REFERENCES public.adverts (id) ON DELETE RESTRICT,
    CONSTRAINT advert_translations_language_code_fkey
        FOREIGN KEY (language_code) REFERENCES public.languages (code) ON DELETE RESTRICT,
    CONSTRAINT advert_translations_advert_language_uq UNIQUE (advert_id, language_code)
);

CREATE SEQUENCE public.advert_translations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.advert_translations_id_seq OWNED BY public.advert_translations.id;

ALTER TABLE ONLY public.advert_translations
    ALTER COLUMN id SET DEFAULT nextval('public.advert_translations_id_seq'::regclass);

CREATE UNIQUE INDEX ux_advert_translations_lang_slug_lower
    ON public.advert_translations (language_code, lower(slug));