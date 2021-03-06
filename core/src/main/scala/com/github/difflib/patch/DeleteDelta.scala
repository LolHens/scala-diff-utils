package com.github.difflib.patch

import com.github.difflib.patch.Patchable._

/**
  * Describes the delete-delta between original and revised texts.
  *
  * @author <a href="dm.naumenko@gmail.com">Dmitry Naumenko</a>
  * @param T The type of the compared elements in the 'lines'.
  */
class DeleteDelta[T](original: Chunk[T],
                     revised: Chunk[T]) extends AbstractDelta[T](DeltaType.Delete, original, revised) {
  @throws[PatchFailedException]
  override def applyTo[F](target: F)(implicit patchable: Patchable[F, T]): F = {
    verifyChunk(patchable.toSeq(target))
    target.remove(original.position, original.size)
  }

  override def restore[F](target: F)(implicit patchable: Patchable[F, T]): F =
    target.insert(revised.position, original.lines)

  override def toString =
    s"[DeleteDelta, position: ${original.position}, lines: ${original.lines.mkString("[", ", ", "]")}]"
}
