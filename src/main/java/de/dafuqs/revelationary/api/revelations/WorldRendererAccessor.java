package de.dafuqs.revelationary.api.revelations;

/**
 * Fetch a WorldRenderer instance to re-render all chunks
 * If you reveal/disguise blocks manually you should trigger this
 * to apply your changes to all visible chunks
 */
public interface WorldRendererAccessor {
	void revelationary$rebuildAllChunks();
}